package com.example.core_sync.data.workManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.core_sync.domain.repository.DownloadRepository
import com.example.core_sync.domain.feature.DownloadFileManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.example.core_ui.R
import com.example.storage.data.model.DownloadingState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.io.File
import javax.inject.Named
import kotlin.getValue

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted val appContext : Context,
    @Assisted workerParams: WorkerParameters,
    private val fileManager: DownloadFileManager,
    private val downloadRepository: DownloadRepository,
    @Named("Download-Client") private val okHttpClient: OkHttpClient
) :
    CoroutineWorker(appContext, workerParams ) {

    private val serviceNotificationManager by lazy { appContext.getSystemService(NotificationManager::class.java) }

    override suspend fun doWork(): Result {
       val url = inputData.getString(URL)?.takeIf {it.isNotBlank()} ?: return Result.failure()
        val id = inputData.getString(ID)?.takeIf {it.isNotBlank()} ?: return Result.failure()
        val title = inputData.getString(TITLE) ?: ""


        return try {
            updateNotification(title,-1)
            downloadRepository.updateItem(id = id, state = DownloadingState.DOWNLOADING, filePath = null)
            val file = downloadTrackFile(id = id, url = url, title = title)
            downloadRepository.updateItem(id = id, state = DownloadingState.DOWNLOADED_SUCCESSFULLY, filePath = file.absolutePath)
             Result.success()
        } catch (e : CancellationException){
            withContext(NonCancellable){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (stopReason == WorkInfo.STOP_REASON_CANCELLED_BY_APP) {
                        downloadRepository.deleteItemById(id)
                    } else{
                        downloadRepository.updateItem(id, DownloadingState.QUEUED,null)
                    }
                }
            }
            throw e
        } catch (e: IOException){
           if(runAttemptCount > MAX_ATTEMPT){
               downloadRepository.updateItem(id = id, state = DownloadingState.FAILED, filePath = null )
               Result.failure()
           } else{
               downloadRepository.updateItem(id = id, state = DownloadingState.QUEUED, filePath = null )
               Result.retry()
           }
        } catch (e: Exception){
            downloadRepository.updateItem(id = id, state = DownloadingState.FAILED, filePath = null )
             Result.failure()

         }

    }

    private suspend fun downloadTrackFile(id : String, url: String, title : String) : File =
        withContext(Dispatchers.IO){
        val readyMusicPath = fileManager.readyFilePath(id)
            val tempMusicPath = fileManager.partFilePath(id)
            var downloaded = 0L
            var total = -1L

            try {
                val request = Request.Builder().url(url).build()

                okHttpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        if (response.code >= 500 || response.code == 429) {
                            throw IOException("Server error ${response.code}")
                        } else {
                            throw IllegalStateException("HTTP ${response.code}")
                        }
                    }

                    val body = response.body ?: throw IOException("Empty body")
                    total = body.contentLength()

                    body.byteStream().use { input ->
                        tempMusicPath.outputStream().use { output ->
                            val buffer = ByteArray(8 * 1024)
                            var lastPercent = -1

                            while (true) {
                                ensureActive()
                                val read = input.read(buffer)
                                if (read == -1) break

                                output.write(buffer, 0, read)
                                downloaded += read

                                if (total > 0) {
                                    val percent = (downloaded * 100 / total).toInt()
                                    if (percent != lastPercent) {
                                        lastPercent = percent
                                        setProgress(workDataOf(PROGRESS to percent))
                                        updateNotification(title, percent)
                                    }
                                }
                            }
                        }
                    }
                }

                if (total > 0 && downloaded != total) {
                    throw IOException("Incomplete: $downloaded/$total")
                }

                if (!tempMusicPath.renameTo(readyMusicPath)) throw IOException("Rename failed")
                readyMusicPath
            } catch (e: Throwable) {
                tempMusicPath.delete()
                throw e
            }

    }

    //Under api 12, this foreground service will work correctly, but above in most of the case it will throw exception
    override suspend fun getForegroundInfo(): ForegroundInfo {
        val title = inputData.getString(TITLE) ?:""
        return createForegroundInfo(title)
    }

      fun createForegroundInfo(title : String,percent: Int = -1) : ForegroundInfo{

        createChannel()

        val pendingIntent = WorkManager.getInstance(applicationContext)
           .createCancelPendingIntent(this.id)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("$title downloading")
            .setContentText(if(percent>=0) "$percent%" else "")
            .setProgress(100,percent.coerceAtLeast(0),percent<0)
            .setSmallIcon(R.drawable.download)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .addAction(R.drawable.download,"Cancel",pendingIntent)
            .build()

        return ForegroundInfo(NOTIFICATION_ID + this.id.hashCode(),notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)

    }
    private suspend fun updateNotification(title: String, percent: Int){
        try {
            setForeground(createForegroundInfo(title, percent))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            //Log.w("DownloadWorker", "Foreground update failed", e)
        }
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,"Downloads", NotificationManager.IMPORTANCE_HIGH)
            serviceNotificationManager.createNotificationChannel(channel)
        }

    }


    companion object {
        const val CHANNEL_ID = "WorkManager_Notification"
        const val URL = "url"
        const val TITLE = "title"
        const val ID = "id"
        const val MAX_ATTEMPT = 3
        const val PROGRESS = "progress"
        const val NOTIFICATION_ID = 200
    }


}



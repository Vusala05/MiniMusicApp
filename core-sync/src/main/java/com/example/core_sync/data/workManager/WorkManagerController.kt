package com.example.core_sync.data.workManager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.core_sync.domain.repository.DownloadRepository
import com.example.core_sync.data.model.DownloadRequest
import com.example.core_sync.domain.feature.DownloadController
import com.example.core_sync.domain.feature.DownloadFileManager
import com.example.storage.data.model.DownloadingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkManagerController @Inject constructor(
    private val workManager: WorkManager,
    val downloadRepository: DownloadRepository,
    val fileManager: DownloadFileManager
) : DownloadController {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .build()
    override suspend fun download(downloadRequest: DownloadRequest) {
        val existingItem = downloadRepository.getDownloadItemById(downloadRequest.id)
        if(existingItem?.state == DownloadingState.DOWNLOADED_SUCCESSFULLY &&
            fileManager.readyFilePath(downloadRequest.id).exists()) return

       // if(!trackResponseDO.allowedDownloadAudio || audioDownloadUrl.isNullOrBlank()) return
        downloadRepository.addItem(
            id = downloadRequest.id, DownloadingState.QUEUED)

        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workDataOf(
                DownloadWorker.TITLE to downloadRequest.title,
                DownloadWorker.ID to downloadRequest.id,
                "url" to downloadRequest.url))
            .setConstraints(constraints)
            .addTag(WORK_TAG)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL,30, TimeUnit.SECONDS)
            .build()

        workManager.enqueueUniqueWork("${WORK_TAG}_${downloadRequest.id}", ExistingWorkPolicy.KEEP,request)
    }

    override suspend fun cancelChosenDownloading(id: String) {
        workManager.cancelUniqueWork("${WORK_TAG}_${id}")
        fileManager.deleteFile(id)
        downloadRepository.deleteItemById(id)

    }


    override suspend fun cancelAllDownloading() {
        workManager.cancelAllWorkByTag(WORK_TAG)
        downloadRepository.deleteAllUnfinishedWorks()
    }

    override fun observeProgress(id: String): Flow<Int?> {
      return  workManager.getWorkInfosForUniqueWorkFlow("${WORK_TAG}_${id}")
            .map { workInfos ->
                val info = workInfos.firstOrNull()
                if(info?.state == WorkInfo.State.RUNNING){
                    info.progress.getInt(DownloadWorker.PROGRESS,-1).takeIf { it>=0 }
            }
                else null

        }
    }

    companion object{
        const val WORK_TAG = "download"
    }
}
package com.example.musicapplication

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.api.usecases.CleanAllOrphanMusicsInfo
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var cleanAllOrphanMusicsInfo : CleanAllOrphanMusicsInfo
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        scope.launch {
            try {
                cleanAllOrphanMusicsInfo()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
            }
        }
    }
}
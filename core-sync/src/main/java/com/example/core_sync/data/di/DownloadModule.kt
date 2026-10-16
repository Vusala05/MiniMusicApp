package com.example.core_sync.data.di

import android.content.Context
import androidx.work.WorkManager
import com.example.core_sync.data.workManager.WorkManagerController
import com.example.core_sync.domain.feature.DownloadController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadModule {

    @Binds
    @Singleton
    abstract fun bindDownloadController(workManagerController: WorkManagerController): DownloadController

    companion object {
        @Provides
        @Singleton
        fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
            WorkManager.getInstance(context)
    }
}

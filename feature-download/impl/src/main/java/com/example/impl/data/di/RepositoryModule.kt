package com.example.feature_download.impl.data.di

import com.example.api.repository.DownloadMusicRepository
import com.example.core_data.dataSource.DownloadRepositoryImpl
import com.example.core_data.feauture.DownloadRepository
import com.example.impl.data.repositoryImpl.DownloadMusicRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMusicDownloadRepository(downloadMusicRepositoryImpl: DownloadMusicRepositoryImpl) : DownloadMusicRepository
}
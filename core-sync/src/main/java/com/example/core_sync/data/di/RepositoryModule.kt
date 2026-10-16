package com.example.core_sync.data.di

import com.example.core_sync.data.repositoryImpl.DownloadRepositoryImpl
import com.example.core_sync.domain.repository.DownloadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDownloadRepository( downloadRepositoryImpl: DownloadRepositoryImpl) : DownloadRepository
}
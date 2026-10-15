package com.example.core_data.di

import com.example.core_data.dataSource.DownloadRepositoryImpl
import com.example.core_data.feauture.DownloadRepository
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
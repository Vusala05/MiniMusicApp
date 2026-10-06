package com.example.impl.data.di

import com.example.api.repository.HomeRepository
import com.example.impl.data.repositoryImpl.HomeRepositoryImpl
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
    abstract fun bindHomeRepository( homeRepositoryImpl: HomeRepositoryImpl) : HomeRepository
}
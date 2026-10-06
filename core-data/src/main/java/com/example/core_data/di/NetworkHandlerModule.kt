package com.example.core_data.di

import com.example.core_data.feauture.GlobalNetworkHandler
import com.example.core_data.model.GlobalNetworkHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
    abstract class NetworkHandlerModule {

        @Binds
        @Singleton
        abstract fun bindGlobalNetwork( globalNetworkHandlerImpl: GlobalNetworkHandlerImpl) : GlobalNetworkHandler


        companion object {
            @Provides
            @Singleton
            fun provideApplicationScope(): CoroutineScope =
                CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }
    }

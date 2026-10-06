package com.example.impl.data.di

import com.example.core_data.di.NetworkModule
import com.example.impl.data.dataSource.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Provides
    @Singleton
    fun provideHomeDataSource(
        @Named("Main-Retrofit") retrofit: Retrofit
    )  = retrofit.create(DataSource::class.java)

}
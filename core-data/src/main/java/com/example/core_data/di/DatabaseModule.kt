package com.example.core_data.di

import android.content.Context
import androidx.room3.Room
import com.example.core_data.dataSource.AppDatabase
import com.example.core_data.dataSource.DownloadDao
import com.example.core_data.dataSource.DownloadMusicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "APP_DATABASE"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    @Provides
    @Singleton
    fun provideDownloadDao(database: AppDatabase): DownloadDao {
        return database.downloadDao()
    }

    @Provides
    @Singleton
    fun provideDownloadMusicDao(database: AppDatabase): DownloadMusicDao {
        return database.downloadMusicDao()
    }
}
package com.example.core_data.dataSource

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.core_data.model.DownloadedEntity
import com.example.core_data.model.DownloadedMusicEntity
import com.example.core_data.model.DownloadedTrackView

@Database(entities = [DownloadedEntity::class, DownloadedMusicEntity::class],
    views = [DownloadedTrackView::class],
    version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun downloadDao() : DownloadDao
    abstract fun downloadMusicDao() : DownloadMusicDao
}
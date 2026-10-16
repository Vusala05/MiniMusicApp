package com.example.storage.data.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.storage.data.model.DownloadedEntity
import com.example.storage.data.model.DownloadedMusicEntity
import com.example.storage.data.model.DownloadedTrackView

@Database(entities = [DownloadedEntity::class, DownloadedMusicEntity::class],
    views = [DownloadedTrackView::class],
    version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun downloadDao() : DownloadDao
    abstract fun downloadMusicDao() : DownloadMusicDao
}
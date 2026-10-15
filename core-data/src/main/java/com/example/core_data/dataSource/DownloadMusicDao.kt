package com.example.core_data.dataSource

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.core_data.model.DownloadedMusicEntity
import com.example.core_data.model.DownloadedTrackView
import com.example.core_data.model.DownloadingState
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadMusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMusic(downloadedMusicEntity: DownloadedMusicEntity)

    @Query("SELECT * FROM downloaded_track_view WHERE state = :state")
    fun observeDownloadedMusics(state: DownloadingState = DownloadingState.DOWNLOADED_SUCCESSFULLY): Flow<List<DownloadedTrackView>>

     @Query("DELETE FROM downloaded_musics WHERE id = :id")
     suspend fun deleteDownloadedMusicInfo(id: String)

    @Query("DELETE FROM downloaded_musics WHERE id NOT IN (SELECT id FROM downloaded)")
    suspend fun deleteAllUnfinishedMusicInfo()
}
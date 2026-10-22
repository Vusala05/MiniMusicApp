package com.example.storage.data.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.storage.data.model.DownloadedMusicEntity
import com.example.storage.data.model.DownloadingState
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadMusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMusic(downloadedMusicEntity: DownloadedMusicEntity)

    @Query(" SELECT * FROM downloaded_musics WHERE id IN (SELECT id FROM downloaded WHERE state = :state)")
    fun observeDownloadedMusics(state: DownloadingState = DownloadingState.DOWNLOADED_SUCCESSFULLY): Flow<List<DownloadedMusicEntity>>

    @Query("SELECT * FROM downloaded_musics WHERE id = :id AND id IN (SELECT id FROM downloaded WHERE state = :state)")
    suspend fun getDownloadedMusicById(id : String,state: DownloadingState = DownloadingState.DOWNLOADED_SUCCESSFULLY): DownloadedMusicEntity?

    @Query("DELETE FROM downloaded_musics WHERE id = :id")
     suspend fun deleteDownloadedMusicInfo(id: String)

    @Query("DELETE FROM downloaded_musics WHERE id NOT IN (SELECT id FROM downloaded)")
    suspend fun cleanAllUnfinishedMusicInfo()
}
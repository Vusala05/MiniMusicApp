package com.example.storage.data.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.storage.data.model.DownloadedEntity
import com.example.storage.data.model.DownloadingState
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDownloadItem(download: DownloadedEntity)

    @Query("SELECT * FROM downloaded WHERE id = :id")
    suspend fun getItem(id: String): DownloadedEntity?

    @Query("SELECT * FROM downloaded WHERE id = :id")
    fun observeItem(id: String): Flow<DownloadedEntity?>

    @Query("UPDATE downloaded SET state = :state,filePath = IFNULL(:path,filePath) WHERE id = :id")
    suspend fun updateItem(id: String, state: DownloadingState, path : String?)

    @Query("DELETE FROM downloaded WHERE id = :id")
    suspend fun deleteDownloadInfo(id: String)

    @Query("DELETE FROM downloaded WHERE state !=:completedState")
    suspend fun deleteAllUnfinishedDownloadInfo(completedState : DownloadingState = DownloadingState.DOWNLOADED_SUCCESSFULLY)

}

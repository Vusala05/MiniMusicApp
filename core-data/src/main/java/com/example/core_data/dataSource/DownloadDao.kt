package com.example.core_data.dataSource

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.core_data.model.DownloadedEntity
import com.example.core_data.model.DownloadingState
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
    suspend fun updateItem(id: String, state: DownloadingState,path : String?)

    @Query("DELETE FROM downloaded WHERE id = :id")
    suspend fun deleteDownloadInfo(id: String)

    @Query("DELETE FROM downloaded WHERE state !=:completedState")
    suspend fun deleteAllUnfinishedDownloadInfo(completedState : DownloadingState = DownloadingState.DOWNLOADED_SUCCESSFULLY)






    /*    @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun upsertMusicInfo(music: DownloadedMusicEntity)

        @Transaction
        suspend fun addDownloadMusicInfo(download: DownloadedEntity,music: DownloadedMusicEntity){
            upsertDownloadInfo(download)
            upsertMusicInfo(music)
        }*/

    /* @Query("SELECT * FROM downloaded_track_view WHERE status = :state")
    fun getDownloadedMusics(state: DownloadingState = DownloadingState.DOWNLOADED_SUCCESSFULLY): Flow<List<DownloadedTrackView>>
*/

    /* @Query("DELETE FROM downloaded_musics WHERE id = :id")
    suspend fun deleteDownloadedMusicInfo(id: String)*/

   /* @Transaction
    suspend fun deleteDownloadedMusic(id: String){
        deleteDownloadInfo(id)
        deleteDownloadedMusicInfo(id)
    }*/



 /*   @Query("DELETE FROM downloaded_musics WHERE id NOT IN (SELECT id FROM downloaded)")
    suspend fun deleteAllUnfinishedMusicInfo()
    @Transaction
    suspend fun deleteUnfinishedWorks(){
        deleteAllUnfinishedDownloadInfo()
        deleteAllUnfinishedMusicInfo()
    }*/
}

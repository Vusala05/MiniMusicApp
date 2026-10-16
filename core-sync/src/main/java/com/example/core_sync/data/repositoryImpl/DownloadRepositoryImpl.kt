package com.example.core_sync.data.repositoryImpl

import com.example.core_sync.domain.repository.DownloadRepository
import com.example.storage.data.database.DownloadDao
import com.example.storage.data.model.DownloadedEntity
import com.example.storage.data.model.DownloadingState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
    val dao: DownloadDao
) : DownloadRepository {

    override suspend fun getDownloadItemById(id: String): DownloadedEntity? {
        return dao.getItem(id = id)
    }

    override fun observeCurrentItem(id: String): Flow<DownloadedEntity?> {
        return dao.observeItem(id)
    }

    override suspend fun addItem(
        id: String,
        state: DownloadingState
    ) {
        dao.upsertDownloadItem(DownloadedEntity(id = id, state = state))
    }

    override suspend fun updateItem(
        id: String,
        state: DownloadingState,
        filePath: String?
    ) {
        dao.updateItem(id = id, state = state, path = filePath)

    }

    override suspend fun deleteItemById(id: String) {
        dao.deleteDownloadInfo(id)
    }

    override suspend fun deleteAllUnfinishedWorks() {
        dao.deleteAllUnfinishedDownloadInfo()
    }
/*
    override suspend fun getDownloadMusicById(id: String): DownloadedEntity? {
    }

    override fun observeCurrentTrack(id: String): Flow<DownloadedEntity?> {
    }


    override fun observeAllDownloadedMusics(): Flow<List<DownloadedTrackView>> {
        return dao.getDownloadedMusics()
    }
*/


   /* override suspend fun addMusic(
        id: String,
        url: String,
        state: DownloadingState,
        name : String,
        duration: Int,
        artistId : String,
        artistName : String,
        audio : String,
        image: String
    ) {
       dao.addDownloadMusicInfo(download = DownloadedEntity(id = id,url = url, urlPath = null, status = state),
           music = DownloadedMusicEntity(id,name,duration,artistId,artistName,audio,image )
       )
    }*/

   /* override suspend fun updateMusic(
        id: String,
        state: DownloadingState,
        fullPath: String?
    ) {
        dao.updateStatus(id = id, status = state, path = fullPath)
    }*/

    /*override suspend fun deleteById(id: String) {
        dao.deleteDownloadedMusic(id)
    }*/


}
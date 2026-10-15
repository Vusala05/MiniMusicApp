package com.example.impl.data.repositoryImpl

import com.example.api.repository.DownloadMusicRepository
import com.example.core_data.dataSource.DownloadMusicDao
import com.example.core_data.model.DownloadedMusicEntity
import com.example.core_data.model.DownloadedTrackView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadMusicRepositoryImpl @Inject constructor(
   val dao: DownloadMusicDao
) : DownloadMusicRepository {
    override suspend fun addMusic(downloadedMusicEntity: DownloadedMusicEntity) {
        dao.addMusic(downloadedMusicEntity)
    }
    override fun observeDownloadedMusics(): Flow<List<DownloadedTrackView>> {
       return dao.observeDownloadedMusics()
    }

    override suspend fun deleteDownloadMusicById(id: String) {
        dao.deleteDownloadedMusicInfo(id)
    }

    override suspend fun deleteAllDownloadMusics() {
        dao.deleteAllUnfinishedMusicInfo()
    }
}
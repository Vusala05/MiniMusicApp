package com.example.impl.data.repositoryImpl

import com.example.api.model.DownloadTrackModel
import com.example.api.model.toUiModel
import com.example.api.repository.DownloadMusicRepository
import com.example.storage.data.database.DownloadMusicDao
import com.example.storage.data.model.DownloadedMusicEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DownloadMusicRepositoryImpl @Inject constructor(
   val dao: DownloadMusicDao
) : DownloadMusicRepository {
    override suspend fun addMusic(downloadedMusicEntity: DownloadedMusicEntity) {
        dao.addMusic(downloadedMusicEntity)
    }
    override fun observeDownloadedMusics(): Flow<List<DownloadTrackModel>> {
       return dao.observeDownloadedMusics().map { it.map { it.toUiModel()  } }
    }

    override suspend fun getDownloadedMusicById(id : String): DownloadTrackModel? {
        return dao.getDownloadedMusicById(id = id)?.toUiModel()
    }

    override suspend fun deleteDownloadMusicById(id: String) {
        dao.deleteDownloadedMusicInfo(id)
    }

    override suspend fun deleteAllDownloadMusics() {
        dao.deleteAllUnfinishedMusicInfo()
    }
}
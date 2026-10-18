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


}
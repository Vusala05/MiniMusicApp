package com.example.core_sync.domain.repository

import com.example.storage.data.model.DownloadedEntity
import com.example.storage.data.model.DownloadingState
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {

    suspend fun getDownloadItemById(id: String): DownloadedEntity?
    fun observeCurrentItem(id : String) : Flow<DownloadedEntity?>
    suspend fun addItem(id: String,state: DownloadingState)
    suspend fun updateItem(id: String, state: DownloadingState, filePath : String?)
    suspend fun deleteItemById(id: String)
    suspend fun deleteAllUnfinishedWorks()
}
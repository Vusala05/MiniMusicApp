package com.example.core_data.feauture

import com.example.core_data.model.DownloadedEntity
import com.example.core_data.model.DownloadingState
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {

    suspend fun getDownloadItemById(id: String): DownloadedEntity?
    fun observeCurrentItem(id : String) : Flow<DownloadedEntity?>
   // fun observeAllDownloadedMusics(): Flow<List<DownloadedTrackView>>
    suspend fun addItem(id: String,state:DownloadingState)
    suspend fun updateItem(id: String,state: DownloadingState, filePath : String?)
    suspend fun deleteItemById(id: String)
    suspend fun deleteAllUnfinishedWorks()
}
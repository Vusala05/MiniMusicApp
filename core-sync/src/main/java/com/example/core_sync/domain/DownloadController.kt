package com.example.core_sync.domain

import com.example.core_sync.data.model.DownloadRequest
import kotlinx.coroutines.flow.Flow

interface DownloadController {

    suspend fun download(downloadRequest: DownloadRequest)
    suspend fun cancelChosenDownloading(id : String)
    suspend fun cancelAllDownloading()
    fun observeProgress(id : String) : Flow<Int?>
}
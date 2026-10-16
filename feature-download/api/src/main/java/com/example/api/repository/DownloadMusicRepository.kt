package com.example.api.repository

import com.example.api.model.DownloadTrackModel
import com.example.storage.data.model.DownloadedMusicEntity
import kotlinx.coroutines.flow.Flow

interface DownloadMusicRepository {
    suspend fun addMusic(downloadedMusicEntity: DownloadedMusicEntity)
    fun observeDownloadedMusics() : Flow<List<DownloadTrackModel>>
    suspend fun deleteDownloadMusicById(id: String)
    suspend fun deleteAllDownloadMusics()

}
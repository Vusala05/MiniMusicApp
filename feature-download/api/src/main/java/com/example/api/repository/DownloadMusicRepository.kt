package com.example.api.repository

import com.example.core_data.model.DownloadedMusicEntity
import com.example.core_data.model.DownloadedTrackView
import kotlinx.coroutines.flow.Flow

interface DownloadMusicRepository {
    suspend fun addMusic(downloadedMusicEntity: DownloadedMusicEntity)
    fun observeDownloadedMusics() : Flow<List<DownloadedTrackView>>
    suspend fun deleteDownloadMusicById(id: String)
    suspend fun deleteAllDownloadMusics()

}
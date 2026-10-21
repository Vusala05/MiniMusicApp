package com.example.api.repository

import com.example.core_domain.model.TrackResponseDO
import com.example.storage.data.model.DownloadedMusicEntity
import kotlinx.coroutines.flow.Flow

interface DownloadMusicRepository {
    suspend fun addMusic(downloadedMusicEntity: DownloadedMusicEntity)
    fun observeDownloadedMusics() : Flow<List<TrackResponseDO>>
    suspend fun getDownloadedMusicById(id: String) : TrackResponseDO?

    suspend fun deleteDownloadMusicById(id: String)
    suspend fun deleteAllDownloadMusics()

}
package com.example.api.usecases

import com.example.api.repository.DownloadMusicRepository
import com.example.core_data.model.DownloadedMusicEntity
import javax.inject.Inject

class AddDownloadMusicUseCase @Inject constructor(
    val downloadMusicRepository: DownloadMusicRepository
) {
    suspend operator fun invoke(downloadedMusicEntity: DownloadedMusicEntity) {
        return downloadMusicRepository.addMusic(downloadedMusicEntity)
    }
}
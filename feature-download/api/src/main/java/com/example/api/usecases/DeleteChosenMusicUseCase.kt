package com.example.api.usecases

import com.example.api.repository.DownloadMusicRepository
import com.example.core_sync.domain.DownloadController
import javax.inject.Inject


class DeleteChosenMusicUseCase @Inject constructor(
    val downloadMusicRepository: DownloadMusicRepository,
    val downloadController: DownloadController
) {
    suspend operator fun invoke(id: String){
        downloadController.cancelChosenDownloading(id)
        downloadMusicRepository.deleteDownloadMusicById(id)

    }
}
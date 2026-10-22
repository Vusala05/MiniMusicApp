package com.example.api.usecases

import com.example.api.repository.DownloadMusicRepository
import javax.inject.Inject

data class CleanAllOrphanMusicsInfo @Inject constructor(
    val downloadMusicRepository: DownloadMusicRepository
) {
    suspend operator fun invoke(){
        downloadMusicRepository.cleanOrphanMusicsInfo()
    }
}
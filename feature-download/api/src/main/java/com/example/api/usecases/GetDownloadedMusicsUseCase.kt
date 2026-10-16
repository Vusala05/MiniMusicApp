package com.example.api.usecases

import com.example.api.model.DownloadTrackModel
import com.example.api.repository.DownloadMusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDownloadedMusicsUseCase @Inject constructor(
      val downloadMusicRepository: DownloadMusicRepository
) {
    operator fun invoke() : Flow<List<DownloadTrackModel>>{
        return downloadMusicRepository.observeDownloadedMusics()
    }
}
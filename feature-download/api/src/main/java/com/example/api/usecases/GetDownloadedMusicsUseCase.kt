package com.example.api.usecases

import com.example.api.repository.DownloadMusicRepository
import com.example.core_domain.model.TrackResponseDO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDownloadedMusicsUseCase @Inject constructor(
      val downloadMusicRepository: DownloadMusicRepository
) {
    operator fun invoke() : Flow<List<TrackResponseDO>>{
        return downloadMusicRepository.observeDownloadedMusics()
    }
}
package com.example.api.useCases

import com.example.api.repository.DownloadMusicRepository
import com.example.api.repository.HomeRepository
import com.example.core_domain.model.TrackResponseDO
import com.example.core_domain.model.ResultWrapper
import javax.inject.Inject

class GetMusicDetailUseCase @Inject constructor(
    val homeRepository: HomeRepository,
    val downloadMusicRepository: DownloadMusicRepository
) {
    suspend operator fun invoke(id : String) : ResultWrapper<TrackResponseDO> {
        val downloadedMusic = downloadMusicRepository.getDownloadedMusicById(id)
        if (downloadedMusic != null) {
            return ResultWrapper.Success(data = downloadedMusic)
        } else {
            return homeRepository.getTrackById(id)
        }
    }
}

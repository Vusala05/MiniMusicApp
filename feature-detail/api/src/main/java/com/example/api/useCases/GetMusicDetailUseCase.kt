package com.example.api.useCases

import com.example.api.model.DownloadTrackModel
import com.example.api.repository.DownloadMusicRepository
import com.example.api.repository.HomeRepository
import com.example.api.response.TrackResponseDO
import com.example.core_domain.model.ResultWrapper
import javax.inject.Inject

class GetMusicDetailUseCase @Inject constructor(
    val homeRepository: HomeRepository,
    val downloadMusicRepository: DownloadMusicRepository
) {
    suspend operator fun invoke(id : String) : ResultWrapper<TrackResponseDO> {
        val downloadedMusic = downloadMusicRepository.getDownloadedMusicById(id)
        if (downloadedMusic != null) {
            return ResultWrapper.Success(data = downloadedMusic.toTrackResponseDO())
        } else {
            return homeRepository.getTrackById(id)
        }
    }
}
fun DownloadTrackModel.toTrackResponseDO() = TrackResponseDO(
    id = id,
    name = name,
    duration = duration,
    artistId = artistId,
    artistName = artistName,
    image = image,
    audio = audio,
    audioDownload = "",
    allowedDownloadAudio = true,
    instruments = emptyList()
)
package com.example.api.usecases

import com.example.api.repository.DownloadMusicRepository
import com.example.core_domain.model.TrackResponseDO
import com.example.core_sync.data.model.DownloadRequest
import com.example.core_sync.domain.feature.DownloadController
import com.example.storage.data.model.DownloadedMusicEntity
import javax.inject.Inject

class DownloadMusicUseCase @Inject constructor(
    val downloadMusicRepository: DownloadMusicRepository,
    val downloadController: DownloadController
) {
    suspend operator fun invoke(trackResponseDO: TrackResponseDO){
        val urlForDownload = trackResponseDO.audioDownload
        val trackId = trackResponseDO.id
        if( trackId.isBlank() || urlForDownload.isNullOrBlank() ) return
        downloadMusicRepository.addMusic(
            DownloadedMusicEntity(
                trackId,
                trackResponseDO.name,
                trackResponseDO.duration,
                trackResponseDO.artistId,
                trackResponseDO.artistName,
                trackResponseDO.audio,
                trackResponseDO.image
            )
        )
        downloadController.download(DownloadRequest(id= trackId, url = urlForDownload,title = trackResponseDO.name))

    }
}
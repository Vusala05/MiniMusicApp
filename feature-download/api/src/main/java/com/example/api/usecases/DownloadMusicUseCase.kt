package com.example.api.usecases

import com.example.api.repository.DownloadMusicRepository
import com.example.core_sync.data.model.DownloadRequest
import com.example.core_sync.domain.feature.DownloadController
import com.example.storage.data.model.DownloadedMusicEntity
import javax.inject.Inject

class DownloadMusicUseCase @Inject constructor(
    val downloadMusicRepository: DownloadMusicRepository,
    val downloadController: DownloadController
) {
    suspend operator fun invoke(id : String, url : String?, title : String,duration: Int,
                                artistId : String, artistName: String,audio: String,
                                image: String){
        if(id.isBlank() || url.isNullOrBlank() ) return
        downloadController.download(DownloadRequest(id= id, url = url,title = title))
        downloadMusicRepository.addMusic(
            DownloadedMusicEntity(
                id,
                title,
                duration,
                artistId,
                artistName,
                audio,
                image
            )
        )
    }
}
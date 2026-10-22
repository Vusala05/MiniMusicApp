package com.example.api.usecases

import com.example.api.repository.DownloadMusicRepository
import com.example.core_domain.model.AppError
import com.example.core_domain.model.ErrorModelDo
import com.example.core_domain.model.ErrorType
import com.example.core_domain.model.ResultWrapper
import com.example.core_domain.model.TrackResponseDO
import com.example.core_sync.data.model.DownloadRequest
import com.example.core_sync.domain.feature.DownloadController
import com.example.storage.data.model.DownloadedMusicEntity
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class DownloadMusicUseCase @Inject constructor(
    val downloadMusicRepository: DownloadMusicRepository,
    val downloadController: DownloadController
) {
    suspend operator fun invoke(trackResponseDO: TrackResponseDO) : ResultWrapper<Unit>{
        val urlForDownload = trackResponseDO.audioDownload
        val trackId = trackResponseDO.id
        if( trackId.isBlank() || urlForDownload.isNullOrBlank() ) {
            return ResultWrapper.Error(
                AppError.BusinessError(
                    ErrorModelDo(
                        errorType = ErrorType.API_ERROR,
                        errorMessage = "Download is not available for this track",
                        warnings = null
                    )
                )
            )

        }
       return try {
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
           ResultWrapper.Success(Unit)
       } catch (e : CancellationException){
           throw e
       } catch (e : Exception){
           ResultWrapper.Error(AppError.BusinessError(
               ErrorModelDo(
                   errorType = ErrorType.FILE,
                   errorMessage = e.message,
                   warnings = e.message
               )
           ))
       }


    }
}
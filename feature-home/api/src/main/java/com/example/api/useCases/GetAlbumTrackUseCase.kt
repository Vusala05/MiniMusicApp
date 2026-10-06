package com.example.api.useCases

import com.example.api.repository.HomeRepository
import com.example.api.response.TrackResponseDO
import com.example.core_data.model.ResultWrapper
import javax.inject.Inject

class GetAlbumTrackUseCase @Inject constructor(
    val homeRepository: HomeRepository
){
    suspend operator  fun invoke(albumId : String,offset : Int) : ResultWrapper<List<TrackResponseDO>>{
        return homeRepository.getAlbumTracks(albumId,offset)
    }
}
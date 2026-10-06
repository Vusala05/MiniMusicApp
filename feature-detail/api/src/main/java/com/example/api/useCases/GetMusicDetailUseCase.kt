package com.example.api.useCases

import com.example.api.repository.HomeRepository
import com.example.api.response.TrackResponseDO
import com.example.core_data.model.ResultWrapper
import javax.inject.Inject


class GetMusicDetailUseCase @Inject constructor(
    val homeRepository: HomeRepository
) {
    suspend operator fun invoke(id : String) : ResultWrapper<TrackResponseDO>{
        return homeRepository.getTrackById(id)
    }
}
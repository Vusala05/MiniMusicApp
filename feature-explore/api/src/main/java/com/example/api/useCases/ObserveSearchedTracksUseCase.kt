package com.example.api.useCases

import com.example.api.repository.HomeRepository
import com.example.core_domain.model.ResultWrapper
import com.example.core_domain.model.TrackResponseDO
import javax.inject.Inject

class ObserveSearchedTracksUseCase @Inject constructor(
    val homeRepository: HomeRepository
) {
    suspend operator fun invoke(search : String, offset: Int) : ResultWrapper<List<TrackResponseDO>> {
        return homeRepository.getSearchedTracks(offset = offset,search = search)
    }
}
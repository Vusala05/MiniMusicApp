package com.example.api.useCases

import com.example.api.repository.HomeRepository
import com.example.api.response.ArtistResponseDO
import com.example.core_data.model.ResultWrapper
import javax.inject.Inject

class GetArtistUseCase @Inject constructor(
        val homeRepository: HomeRepository
    ){
        suspend operator  fun invoke(offset : Int) : ResultWrapper<List<ArtistResponseDO>>{
            return homeRepository.getArtist(offset)
        }
    }

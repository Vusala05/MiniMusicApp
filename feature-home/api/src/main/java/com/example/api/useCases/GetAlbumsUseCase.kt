package com.example.api.useCases

import com.example.api.repository.HomeRepository
import com.example.api.response.AlbumResponseDO
import com.example.core_data.model.ResultWrapper
import javax.inject.Inject


class GetAlbumsUseCase @Inject constructor(
   val homeRepository: HomeRepository
){
    suspend operator  fun invoke(tag: String?,offset : Int) : ResultWrapper<List<AlbumResponseDO>>{
        return homeRepository.getAlbums(tag,offset)
    }
}
package com.example.api.useCases

import com.example.api.repository.HomeRepository
import com.example.api.response.TrackResponseDO
import com.example.core_data.model.ResultWrapper
import javax.inject.Inject

//Burada eslinde DetailRepository de api call edib burda alacaqdim datalari, model kimi de home deki
// TrackResponseDao istifade edecekdim:
//    override suspend fun getTrackById(id: String): ResultWrapper<TrackResponseDO> {
//        return handleResultWrapper(result = apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
//            apiCalling ={ dataSource.getSongById(id = id) } ), transform = { result ->
//            result?.firstOrNull()?.toUiModel() ?: TrackResponseDO("","",0,"","","","",emptyList())
//        })
//    }
// amma burda toUiModel istifade edende artiq problem yarandi, Home modulu terefde loop yaranirdi,ona gore ele bir
// basa HomeRepoda yaradib ordan  cagirdim
class GetMusicDetailUseCase @Inject constructor(
    val homeRepository: HomeRepository
) {
    suspend operator fun invoke(id : String) : ResultWrapper<TrackResponseDO>{
        return homeRepository.getTrackById(id)
    }
}
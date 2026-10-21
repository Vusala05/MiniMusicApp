package com.example.api.useCases

import com.example.service.domain.controller.MusicController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDynamicPlayBackStateUseCase @Inject constructor(
    val musicController: MusicController
) {
    operator fun invoke() : Flow<DynamicPlayBackInfo>{
        return musicController.dynamicPlayBackState.map {
            DynamicPlayBackInfo(currentPosition = it.currentPositionMs)
        }
    }

}
data class DynamicPlayBackInfo(
    val currentPosition : Int = 0
)
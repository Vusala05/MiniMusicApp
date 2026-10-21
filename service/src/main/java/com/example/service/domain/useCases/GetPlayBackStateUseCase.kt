package com.example.service.domain.useCases

import com.example.core_domain.model.ButtonState
import com.example.core_domain.model.RepeatMode
import com.example.service.domain.controller.MusicController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject



class GetPlayBackStateUseCase @Inject constructor(
    val musicController: MusicController
) {

     operator fun invoke() : Flow<PlayBackInfo> {
        return musicController.playbackState.map{ currentState ->
            PlayBackInfo(
                 isPlaying = currentState.isPlaying,
                 chosenSong = currentState.currentItem?.mediaId,
                  btnState = currentState.btnState,
                 trackId = currentState.currentItem?.mediaId,
                isShuffleOn = currentState.isShuffleOn,
                repeatMode = currentState.repeatMode,
                hasNext = currentState.hasNext,
                totalDurationMs = currentState.totalDurationMs,
                errorMessage = currentState.errorMessage,
                 currentQueueTrackIds = currentState.currentQueueItemsId.map { it.mediaId })
         }

     }
}

data class PlayBackInfo(
    val isPlaying : Boolean = false,
    val chosenSong : String?=null,
    val currentQueueTrackIds : List<String> = emptyList(),
    val btnState : ButtonState = ButtonState.PLAY,
    val trackId : String?=null,
    val isShuffleOn : Boolean = false,
    val repeatMode : RepeatMode = RepeatMode.REPEAT_MODE_OFF,
    val hasNext : Boolean = false,
    val totalDurationMs : Int = 0,
    val errorMessage : String?=null
)



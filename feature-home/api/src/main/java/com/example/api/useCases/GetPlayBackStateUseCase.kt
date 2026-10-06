package com.example.api.useCases

import com.example.api.response.TrackResponseDO
import com.example.core_data.util.ButtonState
import com.example.core_data.util.RepeatMode
import com.example.service.MusicController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class GetPlayBackStateUseCase @Inject constructor(
    val musicController: MusicController
) {

    // Position  her 500 ms ile deyisdiyinden onlar
    // gelende collect edilmir her defe
     operator fun invoke() : Flow<PlayBackInfo> {
        return musicController.playbackState.distinctUntilChanged { old, new ->
             val eqIsPlaying = old.isPlaying == new.isPlaying
             val eqCurrentTrackId = old.currentItem == new.currentItem
             val eqTrackId = old.currentItem?.mediaId == new.currentItem?.mediaId
             val eqShuffle = old.isShuffleOn == new.isShuffleOn
             val eqRepeat = old.repeatMode == new.repeatMode
             val eqHasNext = old.hasNext == new.hasNext
             val eqBtnState = old.btnState == new.btnState
             val eqQueue = old.currentQueueItemsId.map { it.mediaId } == new.currentQueueItemsId.map { it.mediaId }
             eqIsPlaying && eqCurrentTrackId && eqQueue && eqBtnState && eqTrackId && eqShuffle && eqRepeat && eqHasNext
         }.map{ currentState ->
            PlayBackInfo(
                 isPlaying = currentState.isPlaying,
                 chosenSong = currentState.currentItem?.mediaId,
                  btnState = currentState.btnState,
                 trackId = currentState.currentItem?.mediaId,
                isShuffleOn = currentState.isShuffleOn,
                repeatMode = currentState.repeatMode,
                hasNext = currentState.hasNext,
                currentPositionMs = currentState.currentPositionMs,
                totalDurationMs = currentState.totalDurationMs,
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
    val currentPositionMs : Int = 0


)



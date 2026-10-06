package com.example.ui.SeeAllScreen.SeeAllTrack

import com.example.api.response.TrackResponseDO
import com.example.core_data.util.ButtonState
import com.example.core_ui.pagination.PagingState
import com.example.navigation.DeeplinkNavigator

object SeeAllTrackContract {

    sealed interface Effect{
        data class ShowMessage (val message : String) : Effect
    }

    sealed interface Intent {
     data object LoadNextPage : Intent
        data class OnClickTrackItem(val deeplinkNavigator: DeeplinkNavigator, val trackId : String) : Intent
        data class OnPlayPauseIconClick(val trackId : String) : Intent
    }
    data class State(
        val pagingState: PagingState<TrackResponseDO> = PagingState(),
        val tag : String?=null,
        val albumId: String?=null,
        val chosenSong : String? = null,
        val currentQueueTrackIds : List<String> = emptyList(),
        val isPlaying : Boolean = false,
        val playPauseBtnState : ButtonState = ButtonState.PLAY
    )

}
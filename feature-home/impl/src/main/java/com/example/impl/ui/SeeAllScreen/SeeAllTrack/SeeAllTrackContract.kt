package com.example.impl.ui.SeeAllScreen.SeeAllTrack

import com.example.api.response.TrackResponseDO
import com.example.core_domain.model.ButtonState
import com.example.core_ui.pagination.PagingState
import com.example.navigation.AppRoutes

object SeeAllTrackContract {

    sealed interface Effect{
        data class ShowMessage (val message : String) : Effect
    }

    sealed interface Intent {
     data object LoadNextPage : Intent
        data class OnClickTrackItem(val appRoutes: AppRoutes, val trackId : String) : Intent
        data class OnPlayPauseIconClick(val trackId : String) : Intent
    }
    data class State(
        val pagingState: PagingState<TrackResponseDO> = PagingState(),
        val chosenSong : String? = null,
        val currentQueueTrackIds : List<String> = emptyList(),
        val isPlaying : Boolean = false,
        val playPauseBtnState : ButtonState = ButtonState.PLAY,
        val totalDuration : Int = 0
    )

}
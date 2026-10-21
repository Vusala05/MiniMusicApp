package com.example.impl.ui

import com.example.core_domain.model.ButtonState
import com.example.core_domain.model.TrackResponseDO
import com.example.core_ui.pagination.PagingState
import com.example.navigation.AppRoutes

object ExploreContract {

    sealed interface Effect{
        data class SendMessage(val data : String) : Effect
    }

    sealed interface Intent {
        data class OnTypeForSearch ( val search : String ) : Intent
        data object LoadNextPage : Intent
        data class OnClickItem(val id : String, val appRoutes: AppRoutes) : Intent
        data class OnClickPlayPauseBtn(val id : String) : Intent
    }

    data class State(
        val searchedString : String = "",
            val pagingState: PagingState<TrackResponseDO> = PagingState(),
            val chosenSong : String? = null,
            val currentQueueTrackIds : List<String> = emptyList(),
            val isPlaying : Boolean = false,
            val playPauseBtnState : ButtonState = ButtonState.PLAY,
            val totalDuration : Int = 0
        )

}
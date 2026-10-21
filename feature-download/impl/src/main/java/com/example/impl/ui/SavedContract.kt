package com.example.impl.ui

import com.example.core_domain.model.ButtonState
import com.example.core_domain.model.TrackResponseDO
import com.example.navigation.AppRoutes

sealed interface SavedContract {

    sealed interface Effect{
        data class SendMessage(val message : String) : Effect
    }

    sealed interface Intent{
        data class OnClickItem( val appRoutes: AppRoutes,val id : String) : Intent
        data class OnClickPlayPauseIcon(val id : String) : Intent
    }
    data class State(
        val loading : Boolean = false,
        val downloadedList : List<TrackResponseDO> = emptyList(),
        val chosenSong : String? = null,
        val currentQueueTrackIds : List<String> = emptyList(),
        val isPlaying : Boolean = false,
        val playPauseBtnState : ButtonState = ButtonState.PLAY
    )
}
package com.example.ui

import com.example.api.model.DownloadTrackModel
import com.example.core_domain.model.ButtonState
import com.example.navigation.DeeplinkNavigator

sealed interface SavedContract {

    sealed interface Effect{
        data class SendMessage(val message : String) : Effect
    }

    sealed interface Intent{
        data class OnClickItem( val deeplinkNavigator: DeeplinkNavigator,val id : String) : Intent
        data class OnClickPlayPauseIcon(val id : String) : Intent
    }
    data class State(
        val loading : Boolean = false,
        val downloadedList : List<DownloadTrackModel> = emptyList(),
        val chosenSong : String? = null,
        val currentQueueTrackIds : List<String> = emptyList(),
        val isPlaying : Boolean = false,
        val playPauseBtnState : ButtonState = ButtonState.PLAY
    )
}
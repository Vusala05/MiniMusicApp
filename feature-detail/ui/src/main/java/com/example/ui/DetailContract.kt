package com.example.ui

import com.example.api.response.TrackResponseDO
import com.example.core_data.util.ButtonState
import com.example.core_data.util.RepeatMode
import com.example.service.model.PlaybackState

sealed interface DetailContract {

    sealed interface Effect{
        data class SendMessage(val message : String) : Effect
    }

    sealed interface Intent {
        data object OnBackClick : Intent
        data object OnClickPlayingBtn : Intent
        data class LoadDetailScreen(val id : String) : Intent
        data object OnClickViewLyrics : Intent
        data object OnNextClick : Intent
        data object OnShuffleClick : Intent
        data object OnRepeatClick : Intent
        data object OnDownloadClicked : Intent
        data object OnPreviousClick : Intent
        data class OnSeek(val positionMs : Long ) : Intent


    }

    data class State(
        val isLoading : Boolean = false,
        val musicDetail : TrackResponseDO = TrackResponseDO("","",0,"","","","",emptyList()),
        val isPlaying : Boolean = false,
        val isExported : Boolean = false,
        val currentPositionMs : Int = 0,
        val totalDurationMs : Int = 0,
        val isShuffleOn : Boolean = false,
        val repeatMode : RepeatMode = RepeatMode.REPEAT_MODE_OFF,
        val currentTime : String = "",
        val hasNext : Boolean = false,
        val shuffleEnable : Boolean = false,
        val btnState : ButtonState = ButtonState.PLAY,
        val hasPrevious : Boolean = false
    )

}
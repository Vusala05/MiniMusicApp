package com.example.impl.ui

import com.example.core_domain.model.TrackResponseDO
import com.example.service.domain.useCases.DynamicPlayBackInfo
import com.example.service.domain.useCases.PlayBackInfo
import com.example.core_domain.model.UiDownloadingState

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
        val playbackState: PlayBackInfo = PlayBackInfo(),
        val dynamicPlayBackState: DynamicPlayBackInfo = DynamicPlayBackInfo(),
        val isLoading : Boolean = false,
        val musicDetail : TrackResponseDO = TrackResponseDO("","",0,"","","","","",false),
        val uiDownloadStatus : UiDownloadingState =  UiDownloadingState.Idle,
    )

}
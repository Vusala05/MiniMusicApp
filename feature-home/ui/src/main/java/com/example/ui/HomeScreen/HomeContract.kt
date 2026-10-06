package com.example.ui.HomeScreen

import com.example.api.response.AlbumResponseDO
import com.example.api.response.ArtistResponseDO
import com.example.api.response.TrackResponseDO
import com.example.api.util.Tag
import com.example.core_data.util.ButtonState
import com.example.navigation.AppRoutes
import com.example.navigation.DeeplinkNavigator
import com.example.service.model.PlaybackState

sealed interface HomeContract {

    sealed interface Effect{
        data class SendMessage(val data : String) : Effect
    }

    sealed interface Intent {
        class OnItemClick(val deeplinkNavigator: DeeplinkNavigator, val trackId : String) : Intent
        class OnClickTab(val tag: Tag) : Intent
        class OnPausePlayIconClick(val id : String) : Intent
        class OnSeeALlClick(val appRoutes: AppRoutes) : Intent
    }

    data class State(
        val albumsList : List<AlbumResponseDO> = emptyList(),
        val trackList : List<TrackResponseDO> = emptyList(),
        val artistList : List<ArtistResponseDO> = emptyList(),
        val chosenSong : String? = null,
        val currentQueueTrackIds : List<String> = emptyList(),
        val isPlaying : Boolean = false,
        val isTrackLoading : Boolean = false,
        val isAlbumLoading : Boolean = false,
        val isArtistLoading : Boolean = false,
        val selectedTab : Tag = Tag.All,
        val playPauseBtnState : ButtonState = ButtonState.PLAY
    )


}
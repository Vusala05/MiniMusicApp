package com.example.ui

import androidx.lifecycle.viewModelScope
import com.example.api.useCases.GetPlayBackStateUseCase
import com.example.api.useCases.PlayPauseClickedUseCase
import com.example.api.useCases.PrepareQueueUseCase
import com.example.api.usecases.GetDownloadedMusicsUseCase
import com.example.core_ui.model.BaseViewModel
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Navigator
import com.example.navigation.navigatorDeepLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getDownloadedMusicsUseCase: GetDownloadedMusicsUseCase,
    private val getPlayBackStateUseCase: GetPlayBackStateUseCase,
    private val playPauseClickedUseCase: PlayPauseClickedUseCase,
    private val prepareQueueUseCase: PrepareQueueUseCase,
    private val navigator: Navigator
) : BaseViewModel<SavedContract.State, SavedContract.Effect>
    (initialState = SavedContract.State() ){

        init {
            getAllDownloadedMusics()
            observePlaybackFromSessionController()
        }

    fun handleIntent(intent: SavedContract.Intent){
        when(intent){
            is SavedContract.Intent.OnClickItem -> {
                prepareQueue(intent.id)
                handleDeeplinkNavigation(intent.deeplinkNavigator)
            }
            is SavedContract.Intent.OnClickPlayPauseIcon -> {
              onPlayPauseClicked(intent.id)
            }
        }
    }

    private fun observePlaybackFromSessionController() {
        viewModelScope.launch {
            getPlayBackStateUseCase().collect { playbackInfo->
                updateUiState { it.copy(
                    isPlaying = playbackInfo.isPlaying,
                    chosenSong = playbackInfo.chosenSong,
                    currentQueueTrackIds = playbackInfo.currentQueueTrackIds,
                    playPauseBtnState = playbackInfo.btnState) }

                playbackInfo.errorMessage?.let {
                    sendEffect(SavedContract.Effect.SendMessage(it))
                }
            }
        }

    }

    private fun onPlayPauseClicked(trackId: String) {
        val state = getCurrentState()
        playPauseClickedUseCase(
            currentQueueTrackIds = state.currentQueueTrackIds,
            currentTrackList = state.downloadedList    ,
            currentChosenSong = state.chosenSong,
            trackId = trackId
        )

    }
    private fun getAllDownloadedMusics(){
        viewModelScope.launch {
            getDownloadedMusicsUseCase().collect { downloadedMusicList ->
               updateUiState { it.copy(downloadedList = downloadedMusicList ) }
            }
        }
    }
    private fun prepareQueue(trackId : String){
        val currentState = getCurrentState()
        prepareQueueUseCase(
            currentQueueTrackIds = currentState.currentQueueTrackIds,
            trackList = currentState.downloadedList,
            currentChosenSong = currentState.chosenSong,
            trackId = trackId
        )
    }


    private fun handleDeeplinkNavigation(deeplinkNavigator: DeeplinkNavigator){
      navigator.navigatorDeepLink(deeplinkNavigator){
          navigate(it)
      }
    }

    }
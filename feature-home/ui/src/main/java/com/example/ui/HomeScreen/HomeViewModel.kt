package com.example.ui.HomeScreen

import androidx.lifecycle.viewModelScope
import com.example.api.useCases.GetAlbumsUseCase
import com.example.api.useCases.GetArtistUseCase
import com.example.api.useCases.GetPlayBackStateUseCase
import com.example.api.useCases.GetTracksUseCase
import com.example.api.useCases.PlayPauseClickedUseCase
import com.example.api.useCases.PrepareQueueUseCase
import com.example.core_data.model.ResultWrapper
import com.example.core_data.useCases.HandleErrorUseCase
import com.example.core_ui.model.BaseViewModel
import com.example.navigation.AppRoutes
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Navigator
import com.example.navigation.navigatorDeepLink
import com.example.navigation.navigatorRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getAlbumsUseCase: GetAlbumsUseCase,
    val getTracksUseCase: GetTracksUseCase,
    val getArtistUseCase: GetArtistUseCase,
    val navigator: Navigator,
    val prepareQueueUseCase: PrepareQueueUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val playPauseClickedUseCase: PlayPauseClickedUseCase,
    val getPlayBackStateUseCase: GetPlayBackStateUseCase
) : BaseViewModel<HomeContract.State, HomeContract.Effect>(
    initialState = HomeContract.State()
) {

    init {
        observeFilterAndRefresh()
        observePlaybackFromSessionController()
        getArtists()
    }

    fun handleIntent(intent: HomeContract.Intent){
        when(intent){
            is HomeContract.Intent.OnItemClick -> {
                prepareQueue(intent.trackId)
                handleDeepLinkNavigation(intent.deeplinkNavigator)
            }
            is HomeContract.Intent.OnClickTab -> {
              updateUiState { it.copy(selectedTab = intent.tag) }
            }
            is HomeContract.Intent.OnPausePlayIconClick -> {
               onPlayPauseClicked(intent.id)
            }
            is HomeContract.Intent.OnSeeALlClick -> {
                handleRouteNavigation(intent.appRoutes)
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
                    sendEffect(HomeContract.Effect.SendMessage(it))
                }
            }
        }

    }

    private fun prepareQueue(trackId : String){
        val currentState = getCurrentState()
        prepareQueueUseCase(
            currentQueueTrackIds = currentState.currentQueueTrackIds,
            trackList = currentState.trackList,
            currentChosenSong = currentState.chosenSong,
            trackId = trackId
        )
    }


    private fun onPlayPauseClicked(trackId: String) {
        val state = getCurrentState()
        playPauseClickedUseCase(
            currentQueueTrackIds = state.currentQueueTrackIds,
            currentTrackList = state.trackList,
            currentChosenSong = state.chosenSong,
            trackId = trackId
            )

    }

    private fun getAlbums(tag : String?){
        viewModelScope.launch {
            updateUiState { it.copy(isAlbumLoading = true) }
            when(val res = getAlbumsUseCase(tag, offset = 0)){
             is ResultWrapper.Success ->{
               updateUiState { it.copy(albumsList = res.data, isAlbumLoading = false) }
             }
                is ResultWrapper.Error ->{
                    updateUiState { it.copy(isAlbumLoading = false) }
                    sendEffect(HomeContract.Effect.SendMessage(handleErrorUseCase(res.appError)))
                }
            }
        }
    }

    private fun getTracks(tag: String?){
        viewModelScope.launch {
            updateUiState { it.copy(isTrackLoading = true) }
            when(val res = getTracksUseCase(tag, offset = 0)){
                is ResultWrapper.Success ->{
                    updateUiState { it.copy(trackList = res.data, isTrackLoading = false) }
                }
                is ResultWrapper.Error ->{
                    updateUiState { it.copy(isTrackLoading = false) }
                    sendEffect(HomeContract.Effect.SendMessage(handleErrorUseCase(res.appError)))
                }
            }
        }

    }

    private fun getArtists(){
        viewModelScope.launch {
            updateUiState { it.copy(isArtistLoading = true) }
            when(val res = getArtistUseCase(offset = 0)){
                is ResultWrapper.Success ->{
                    updateUiState { it.copy(artistList = res.data, isArtistLoading = false) }
                }
                is ResultWrapper.Error ->{
                    updateUiState { it.copy(isArtistLoading = false) }
                    sendEffect(HomeContract.Effect.SendMessage(handleErrorUseCase(res.appError)))
                }
            }
        }

    }
    private fun observeFilterAndRefresh(){
        viewModelScope.launch {
            _state.distinctUntilChangedBy {it.selectedTab}
                .collectLatest { currentState ->
                  getAlbums(currentState.selectedTab.value)
                    getTracks(currentState.selectedTab.value)
                }
        }

    }
    private fun handleDeepLinkNavigation(deeplinkNavigator: DeeplinkNavigator){
     navigator.navigatorDeepLink(deeplinkNavigator){
      navigate(it)
     }
    }
    private fun handleRouteNavigation(appRoutes: AppRoutes){
        navigator.navigatorRoute {
            navigate(appRoutes)
        }
    }



}
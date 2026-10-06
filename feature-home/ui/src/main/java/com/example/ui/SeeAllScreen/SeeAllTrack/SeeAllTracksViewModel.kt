package com.example.ui.SeeAllScreen.SeeAllTrack
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.api.useCases.GetAlbumTrackUseCase
import com.example.api.useCases.GetPlayBackStateUseCase
import com.example.api.useCases.GetTracksUseCase
import com.example.api.useCases.PlayPauseClickedUseCase
import com.example.api.useCases.PrepareQueueUseCase
import com.example.core_data.useCases.HandleErrorUseCase
import com.example.core_ui.model.BaseViewModel
import com.example.core_ui.pagination.PaginationHandler
import com.example.navigation.Navigator
import com.example.navigation.Route
import com.example.navigation.navigatorDeepLink
import com.example.service.MusicController
import com.example.ui.SeeALType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllTracksViewModel @Inject constructor(
    val getTracksUseCase: GetTracksUseCase,
    val getPlayBackStateUseCase: GetPlayBackStateUseCase,
    val playPauseClickedUseCase: PlayPauseClickedUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val getAlbumTrackUseCase: GetAlbumTrackUseCase,
    val prepareQueueUseCase: PrepareQueueUseCase,
    val navigator: Navigator,
    val musicController: MusicController,
    savedStateHandle: SavedStateHandle
): BaseViewModel<SeeAllContract.State, SeeAllContract.Effect>(
initialState = SeeAllContract.State()
) {
    private val type: SeeALType? = savedStateHandle["seeALType"]
    private val tag: String? = savedStateHandle["tag"]
    private val albumId: String? = savedStateHandle["albumId"]

    val paginationHandler = PaginationHandler(
        coroutineScope = viewModelScope,
        onGetData = { offset ->
           if(type == SeeALType.SEE_ALL) getTracksUseCase(offset = offset, tag = tag)
            else  getAlbumTrackUseCase(albumId = albumId?:"",offset = offset)
         },
        onError = { appError -> sendEffect(SeeAllContract.Effect.ShowMessage(handleErrorUseCase(appError))) },
        onLoadedNewDataList = { dataList -> musicController.appendToQueue(dataList) }
    )

    init {
        collectPaginationState()
        observePlayBackStateFormMediaController()
        viewModelScope.launch{
            paginationHandler.loadInitialData()
        }
    }


    fun handleIntent(intent: SeeAllContract.Intent) {
        when(intent){
            is SeeAllContract.Intent.OnPlayPauseIconClick -> {
                onPlayPauseClicked(intent.trackId)
            }
            is SeeAllContract.Intent.LoadNextPage -> {
                viewModelScope.launch {
                    paginationHandler.loadNextPage()
                }

            }
            is SeeAllContract.Intent.OnClickTrackItem -> {
                prepareQueue(intent.trackId)
                handleDeepLinkNavigation(intent.route)
            }

        }
    }

    private fun onPlayPauseClicked(trackId: String) {
        val state = getCurrentState()
        playPauseClickedUseCase(
            currentQueueTrackIds = state.currentQueueTrackIds,
            currentTrackList = state.pagingState.dataList,
            currentChosenSong = state.chosenSong,
            trackId = trackId
        )

    }
    private fun prepareQueue(trackId : String){
        val currentState = getCurrentState()
        prepareQueueUseCase(
            currentQueueTrackIds = currentState.currentQueueTrackIds,
            trackList = currentState.pagingState.dataList,
            currentChosenSong = currentState.chosenSong,
            trackId = trackId
        )
    }
    private fun observePlayBackStateFormMediaController(){
        viewModelScope.launch {
            getPlayBackStateUseCase().collect { playBackState ->
              updateUiState { it.copy(
                  isPlaying = playBackState.isPlaying,
                  chosenSong = playBackState.chosenSong,
                  currentQueueTrackIds = playBackState.currentQueueTrackIds,
                  playPauseBtnState = playBackState.btnState)}
            }
        }

    }
    private fun collectPaginationState(){
        viewModelScope.launch {
            paginationHandler.pagingState.collect { paginationState ->
                updateUiState { it.copy(pagingState = paginationState) }
            }
        }

    }

    private fun handleDeepLinkNavigation(route: Route){
        navigator.navigatorDeepLink(route){
            navigate(it)
        }
    }


}

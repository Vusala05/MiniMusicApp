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
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Navigator
import com.example.navigation.navigatorDeepLink
import com.example.service.controller.MusicController
import com.example.ui.HomeScreen.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllTrackViewModel @Inject constructor(
    val getTracksUseCase: GetTracksUseCase,
    val getPlayBackStateUseCase: GetPlayBackStateUseCase,
    val playPauseClickedUseCase: PlayPauseClickedUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val getAlbumTrackUseCase: GetAlbumTrackUseCase,
    val prepareQueueUseCase: PrepareQueueUseCase,
    val navigator: Navigator,
    val musicController: MusicController,
    savedStateHandle: SavedStateHandle
): BaseViewModel<SeeAllTrackContract.State, SeeAllTrackContract.Effect>(
initialState = SeeAllTrackContract.State()
) {
    private val tag: String? = savedStateHandle["tag"]
    private val albumId: String? = savedStateHandle["albumId"]

    val paginationHandler = PaginationHandler(
        coroutineScope = viewModelScope,
        onGetData = { offset ->
           if(albumId!=null) getAlbumTrackUseCase(offset = offset, albumId = albumId)
            else  getTracksUseCase(tag = tag,offset = offset)
         },
        onError = { appError -> sendEffect(SeeAllTrackContract.Effect.ShowMessage(handleErrorUseCase(appError))) },
        onLoadedNewDataList = { dataList -> musicController.appendToQueue(dataList) }
    )

    init {
        collectPaginationState()
        observePlayBackStateFormMediaController()
        viewModelScope.launch{
            paginationHandler.loadInitialData()
        }
    }


    fun handleIntent(intent: SeeAllTrackContract.Intent) {
        when(intent){
            is SeeAllTrackContract.Intent.OnPlayPauseIconClick -> {
                onPlayPauseClicked(intent.trackId)
            }
            is SeeAllTrackContract.Intent.LoadNextPage -> {
                viewModelScope.launch {
                    paginationHandler.loadNextPage()
                }

            }
            is SeeAllTrackContract.Intent.OnClickTrackItem -> {
                prepareQueue(intent.trackId)
                handleDeepLinkNavigation(intent.deeplinkNavigator)
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

                playBackState.errorMessage?.let {
                    sendEffect(SeeAllTrackContract.Effect.ShowMessage(it))
                }
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

    private fun handleDeepLinkNavigation(deeplinkNavigator: DeeplinkNavigator){
        navigator.navigatorDeepLink(deeplinkNavigator){
            navigate(it)
        }
    }


}

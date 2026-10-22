package com.example.impl.ui.SeeAllScreen.SeeAllTrack
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.api.useCases.GetAlbumTrackUseCase
import com.example.service.domain.useCases.GetPlayBackStateUseCase
import com.example.api.useCases.GetTracksUseCase
import com.example.service.domain.useCases.PlayPauseClickedUseCase
import com.example.service.domain.useCases.PrepareQueueUseCase
import com.example.core_domain.useCases.HandleErrorUseCase
import com.example.core_ui.model.BaseViewModel
import com.example.core_ui.pagination.PaginationHandler
import com.example.navigation.AppRoutes
import com.example.navigation.Navigator
import com.example.navigation.navigatorRoute
import com.example.service.domain.controller.MusicController
import com.example.service.domain.useCases.AppendToQueueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllTrackViewModel @Inject constructor(
    val getTracksUseCase: GetTracksUseCase,
    val playPauseClickedUseCase: PlayPauseClickedUseCase,
     handleErrorUseCase: HandleErrorUseCase,
    val getAlbumTrackUseCase: GetAlbumTrackUseCase,
    val prepareQueueUseCase: PrepareQueueUseCase,
    val navigator: Navigator,
    val getPlayBackStateUseCase: GetPlayBackStateUseCase,
    val appendToQueueUseCase: AppendToQueueUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<SeeAllTrackContract.State, SeeAllTrackContract.Effect>(
initialState = SeeAllTrackContract.State(),
    handleErrorUseCase = handleErrorUseCase
) {
    private val route = savedStateHandle.toRoute<AppRoutes.SeeAllTrack>()
    val tag = route.tag
    val albumId = route.albumId

    val paginationHandler = PaginationHandler(
        coroutineScope = viewModelScope,
        onGetData = { offset ->
           if(albumId!=null) getAlbumTrackUseCase(offset = offset, albumId = albumId)
            else  getTracksUseCase(tag = tag,offset = offset)
         },
        onError = { appError -> handleError(appError) },
        onLoadedNewDataList = { dataList -> appendToQueueUseCase(dataList) }
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
                    paginationHandler.loadNextPage()

            }
            is SeeAllTrackContract.Intent.OnClickTrackItem -> {
                prepareQueue(intent.trackId)
                handleNavigation(intent.appRoutes)
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
            getPlayBackStateUseCase().collect { playbackInfo->
                updateUiState { it.copy(
                    isPlaying = playbackInfo.isPlaying,
                    chosenSong = playbackInfo.chosenSong,
                    currentQueueTrackIds = playbackInfo.currentQueueTrackIds,
                    playPauseBtnState = playbackInfo.btnState,
                    totalDuration = playbackInfo.totalDurationMs) }

                playbackInfo.errorMessage?.let {
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

    private fun handleNavigation(appRoutes: AppRoutes){
        navigator.navigatorRoute {
            navigate(appRoutes)
        }
    }

    override suspend fun showErrorMessage(error: String) {
        sendEffect(SeeAllTrackContract.Effect.ShowMessage(error))
    }


}

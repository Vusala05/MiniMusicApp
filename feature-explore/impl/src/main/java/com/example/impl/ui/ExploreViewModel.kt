package com.example.impl.ui

import androidx.lifecycle.viewModelScope
import com.example.api.useCases.ObserveSearchedTracksUseCase
import com.example.core_domain.useCases.HandleErrorUseCase
import com.example.core_ui.model.BaseViewModel
import com.example.core_ui.pagination.PaginationHandler
import com.example.navigation.AppRoutes
import com.example.navigation.Navigator
import com.example.navigation.navigatorRoute
import com.example.service.domain.useCases.GetPlayBackStateUseCase
import com.example.service.domain.useCases.PlayPauseSingleItemUseCase
import com.example.service.domain.useCases.PlaySingleItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ExploreViewModel @Inject constructor(
    val observeSearchedTracksUseCase: ObserveSearchedTracksUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val getPlayBackStateUseCase: GetPlayBackStateUseCase,
    val playSingleItemUseCase: PlaySingleItemUseCase,
    val playPauseSingleItemUseCase : PlayPauseSingleItemUseCase,
    val navigator: Navigator
    ): BaseViewModel<ExploreContract.State, ExploreContract.Effect>(
    initialState = ExploreContract.State()
) {

    val paginationHandler = PaginationHandler(
        coroutineScope = viewModelScope,
        onGetData = { offset ->
            val currentState = getCurrentState()
            observeSearchedTracksUseCase(search = currentState.searchedString, offset = offset)
        },
        onError = { appError ->
            sendEffect(
                ExploreContract.Effect.SendMessage(
                    handleErrorUseCase(
                        appError
                    )
                )
            )
        }
    )

    init {
        observePlayBackStateFormMediaController()
        collectPaginationState()
    }
    private var job: Job? = null
    fun handleIntent(intent: ExploreContract.Intent) {
        when (intent) {
            is ExploreContract.Intent.OnClickItem -> {
                val state = getCurrentState()
                val item = state.pagingState.dataList.find { it.id == intent.id } ?:return
                playSingleItemUseCase(item)
                handleNavigation(intent.appRoutes)
            }

            is ExploreContract.Intent.OnTypeForSearch -> {
                updateUiState { it.copy(searchedString = intent.search) }
                searchStringChange(intent.search)
            }

            is ExploreContract.Intent.OnClickPlayPauseBtn -> {
                onPlayPauseClicked(intent.id)
            }

            is ExploreContract.Intent.LoadNextPage -> {
                paginationHandler.loadNextPage()
            }
        }
    }

    private fun searchStringChange(search: String) {
        job?.cancel()
        if (search.isBlank()) {
            paginationHandler.clear()
            return
        }
        job = viewModelScope.launch {
            delay(0.5.seconds)
            paginationHandler.loadInitialData()
        }


    }

    private fun onPlayPauseClicked(trackId: String) {
        val state = getCurrentState()
        val item = state.pagingState.dataList.find { it.id == trackId  } ?:return
        playPauseSingleItemUseCase(
            item = item
        )

    }
    private fun observePlayBackStateFormMediaController() {
        viewModelScope.launch {
            getPlayBackStateUseCase().collect { playbackInfo ->
                updateUiState {
                    it.copy(
                        isPlaying = playbackInfo.isPlaying,
                        chosenSong = playbackInfo.chosenSong,
                        currentQueueTrackIds = playbackInfo.currentQueueTrackIds,
                        playPauseBtnState = playbackInfo.btnState,
                        totalDuration = playbackInfo.totalDurationMs
                    )
                }

                playbackInfo.errorMessage?.let {
                    sendEffect(ExploreContract.Effect.SendMessage(it))
                }
            }

        }

    }

    private fun collectPaginationState() {
        viewModelScope.launch {
            paginationHandler.pagingState.collect { paginationState ->
                updateUiState { it.copy(pagingState = paginationState) }
            }
        }

    }

    private fun handleNavigation(appRoutes: AppRoutes) {
        navigator.navigatorRoute {
            navigate(appRoutes)
        }

    }
}
package com.example.impl.ui

import androidx.lifecycle.viewModelScope
import com.example.api.useCases.ControlBottomComponents
import com.example.api.useCases.ControlDetailBottomComponentsUseCase
import com.example.service.domain.useCases.GetDynamicPlayBackStateUseCase
import com.example.api.useCases.GetMusicDetailUseCase
import com.example.service.domain.useCases.GetPlayBackStateUseCase
import com.example.api.useCases.ObserveDownloadStatusUseCase
import com.example.api.usecases.DeleteChosenMusicUseCase
import com.example.api.usecases.DownloadMusicUseCase
import com.example.core_domain.model.ResultWrapper
import com.example.core_domain.model.UiDownloadingState
import com.example.core_domain.useCases.HandleErrorUseCase
import com.example.core_ui.model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getMusicDetailUseCase: GetMusicDetailUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val getPlayBackStateUseCase: GetPlayBackStateUseCase,
    val getDynamicPlayBackStateUseCase: GetDynamicPlayBackStateUseCase,
    val controlDetailBottomComponentsUseCase: ControlDetailBottomComponentsUseCase,
    val downloadMusicUseCase: DownloadMusicUseCase,
    val deleteChosenMusicUseCase: DeleteChosenMusicUseCase,
    val observeDownloadStatusUseCase: ObserveDownloadStatusUseCase
) : BaseViewModel<DetailContract.State, DetailContract.Effect>
    (initialState = DetailContract.State() ){

        init {
            observeCurrentTrack()
            observePlaybackState()
            observeProgressbar()
            observeTrackDownloadState()
        }
    fun handleIntent(intent: DetailContract.Intent){
        when(intent){
            is DetailContract.Intent.LoadDetailScreen -> {
             loadMusicDetail(intent.id)
            }
            is DetailContract.Intent.OnClickViewLyrics -> {

            }
            is DetailContract.Intent.OnNextClick -> {
               controlDetailBottomComponentsUseCase(ControlBottomComponents.ToggleNext)
            }
            is DetailContract.Intent.OnPreviousClick -> {
                controlDetailBottomComponentsUseCase(ControlBottomComponents.TogglePrevious)

            }
            is DetailContract.Intent.OnClickPlayingBtn -> {
                controlDetailBottomComponentsUseCase(ControlBottomComponents.TogglePlayPause)

            }
            is DetailContract.Intent.OnBackClick -> {

            }
            is DetailContract.Intent.OnDownloadClicked -> {
                viewModelScope.launch {
                    val currentDetail = getCurrentState().musicDetail
                    when(getCurrentState().uiDownloadStatus){
                        UiDownloadingState.Idle -> downloadMusicUseCase(currentDetail)
                        is UiDownloadingState.InProgress,
                        UiDownloadingState.Completed -> deleteChosenMusicUseCase(currentDetail.id)

                        UiDownloadingState.Hidden -> Unit
                    }
                }

            }
            is DetailContract.Intent.OnSeek -> {
                controlDetailBottomComponentsUseCase(ControlBottomComponents.SeekTo(intent.positionMs))
            }
            is DetailContract.Intent.OnRepeatClick -> {
                controlDetailBottomComponentsUseCase(ControlBottomComponents.ToggleRepeatMode)

            }
            is DetailContract.Intent.OnShuffleClick -> {
                val currentState = getCurrentState()
                controlDetailBottomComponentsUseCase(ControlBottomComponents.SetShuffleEnabled(!currentState.playbackState.isShuffleOn))
            }
        }
    }

    private fun loadMusicDetail(id : String){
        viewModelScope.launch {
            when(val res = getMusicDetailUseCase(id) ){
                is ResultWrapper.Success ->{
                    updateUiState { it.copy(musicDetail = res.data) }
                }
                is ResultWrapper.Error ->{
                    sendEffect(DetailContract.Effect.SendMessage(handleErrorUseCase(res.appError)))
                }

            }
        }

    }
    private fun observePlaybackState() {
        viewModelScope.launch {
            getPlayBackStateUseCase().collectLatest { playBackInfo ->
                updateUiState {
                    it.copy(
                       playbackState = playBackInfo
                    )
                }
                playBackInfo.errorMessage?.let {
                    sendEffect(DetailContract.Effect.SendMessage(it))
                }
            }
        }
    }
    private fun observeProgressbar(){
        viewModelScope.launch {
            getDynamicPlayBackStateUseCase()
                .collect { dynamicPlayBack ->
                  _state.update { it.copy(dynamicPlayBackState = dynamicPlayBack) }
                }
        }
    }

    private fun observeCurrentTrack() {
        viewModelScope.launch {
            getPlayBackStateUseCase().map { it.trackId }
                .filterNotNull()
                .distinctUntilChanged()
                .collectLatest { id ->
                    val currentState = getCurrentState()
                    if (currentState.musicDetail.id != id) loadMusicDetail(id)
                }
        }
    }

    private fun observeTrackDownloadState(){
        viewModelScope.launch {
                _state.distinctUntilChanged { old, new ->
                    val eqMusicDetailId = old.musicDetail.id == new.musicDetail.id
                    val eqAllowed = old.musicDetail.allowedDownloadAudio == new.musicDetail.allowedDownloadAudio
                    eqMusicDetailId && eqAllowed
                }.filterNotNull()
                    .map { it.musicDetail.id to it.musicDetail.allowedDownloadAudio }
                .collectLatest { (id,allowed)->
                    if(!allowed) updateUiState { it.copy(uiDownloadStatus = UiDownloadingState.Hidden) }
                    else{
                        observeDownloadStatusUseCase(id).collect { status ->
                            updateUiState { it.copy(uiDownloadStatus = status) }
                        }
                    }
                }
        }
    }
    }
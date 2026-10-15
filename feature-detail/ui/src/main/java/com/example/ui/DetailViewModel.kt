package com.example.ui

import androidx.lifecycle.viewModelScope
import com.example.api.useCases.GetMusicDetailUseCase
import com.example.api.useCases.GetPlayBackStateUseCase
import com.example.api.useCases.ObserveDownloadStatusUseCase
import com.example.api.usecases.DeleteChosenMusicUseCase
import com.example.api.usecases.DownloadMusicUseCase
import com.example.core_data.model.ResultWrapper
import com.example.core_data.useCases.HandleErrorUseCase
import com.example.core_ui.model.BaseViewModel
import com.example.service.controller.MusicController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.core_data.util.DownloadingStatus
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getMusicDetailUseCase: GetMusicDetailUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val getPlayBackStateUseCase: GetPlayBackStateUseCase,
    val musicController: MusicController,
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
               musicController.next()
            }
            is DetailContract.Intent.OnPreviousClick -> {
               musicController.previous()
            }
            is DetailContract.Intent.OnClickPlayingBtn -> {
                   musicController.togglePlayPause()
            }
            is DetailContract.Intent.OnBackClick -> {

            }
            is DetailContract.Intent.OnDownloadClicked -> {
                viewModelScope.launch {
                    val currentDetail = getCurrentState().musicDetail
                    when(getCurrentState().downloadStatus){
                        DownloadingStatus.Idle -> downloadMusicUseCase(currentDetail.id,currentDetail.audioDownload,currentDetail.name,currentDetail.duration,currentDetail.artistId,currentDetail.artistName,currentDetail.audio,currentDetail.image)
                        is DownloadingStatus.InProgress,
                        DownloadingStatus.Completed -> deleteChosenMusicUseCase(currentDetail.id)

                        DownloadingStatus.Hidden -> Unit
                    }
                }

            }
            is DetailContract.Intent.OnSeek -> {
               musicController.seekTo(intent.positionMs)
            }
            is DetailContract.Intent.OnRepeatClick -> {
              musicController.toggleRepeatMode()
            }
            is DetailContract.Intent.OnShuffleClick -> {
                val currentState = getCurrentState()
               musicController.setShuffleEnabled(!currentState.isShuffleOn)
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
                        isPlaying = playBackInfo.isPlaying,
                        currentPositionMs = playBackInfo.currentPositionMs,
                        totalDurationMs = playBackInfo.totalDurationMs,
                        hasNext = playBackInfo.hasNext,
                        isShuffleOn = playBackInfo.isShuffleOn,
                        repeatMode = playBackInfo.repeatMode,
                        btnState = playBackInfo.btnState
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
            musicController.playbackState
                .map { it.currentPositionMs to it.totalDurationMs }
                .distinctUntilChanged()
                .collect{ (currentPositionMs,totalDurationMs)->
                  _state.update { it.copy(currentPositionMs = currentPositionMs, totalDurationMs = totalDurationMs) }
                }
        }
    }

    private fun observeCurrentTrack() {
        viewModelScope.launch {
            musicController.playbackState
                .map { it.currentItem?.mediaId }
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
            _state.map { it.musicDetail.id to it.musicDetail.allowedDownloadAudio }
                .filterNotNull()
                .distinctUntilChanged()
                .collectLatest { (id,allowed)->
                    if(!allowed) updateUiState { it.copy(downloadStatus = DownloadingStatus.Hidden) }
                    else{
                        observeDownloadStatusUseCase(id).collect { status ->
                            updateUiState { it.copy(downloadStatus = status) }
                        }
                    }
                }
        }
    }
    }


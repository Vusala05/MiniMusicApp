package com.example.core_domain.model

sealed interface UiDownloadingState {
   data object Idle : UiDownloadingState
    data object Completed : UiDownloadingState
    data object Hidden : UiDownloadingState
    data class InProgress(val progress : Int?) : UiDownloadingState
}
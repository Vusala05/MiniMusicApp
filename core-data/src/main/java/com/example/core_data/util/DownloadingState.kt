package com.example.core_data.util

sealed interface DownloadingStatus {
   data object Idle : DownloadingStatus
    data object Completed : DownloadingStatus
    data object Hidden : DownloadingStatus
    data class InProgress(val progress : Int?) : DownloadingStatus
}
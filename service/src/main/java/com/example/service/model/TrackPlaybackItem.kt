package com.example.service.model

import androidx.media3.common.MediaItem
import com.example.core_data.util.ButtonState
import com.example.core_data.util.RepeatMode


data class PlaybackState(
    val queue: List<MediaItem> = emptyList(),
    val currentIndex: Int = -1,
    val isPlaying: Boolean = false,
    val currentItem : MediaItem?=null,
    val currentPositionMs: Int = 0,
    val totalDurationMs: Int = 0,
    val hasNext: Boolean = false,
    val hasPrevious: Boolean = false,
    val isShuffleOn: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.REPEAT_MODE_OFF,
    val btnState : ButtonState = ButtonState.PLAY,
    val currentQueueItemsId : List<MediaItem> = emptyList(),
    val errorMessage : String?=null
) {
}
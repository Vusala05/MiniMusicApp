package com.example.service.controller

import com.example.service.model.PlayableItem
import com.example.service.model.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface MusicController {
    val playbackState: StateFlow<PlaybackState>

    fun bind()
    fun unbind()


    fun <T : PlayableItem> appendToQueue(data: List<T>)
    fun <T : PlayableItem> playQueue(data: List<T>, startIndex: Int)
    fun <T : PlayableItem> setQueue(data: List<T>, startIndex: Int)
    fun seekToIndex(index: Int)
    fun playAtIndex(index: Int)
    fun togglePlayPause()
    fun next()
    fun previous()
    fun seekTo(positionMs: Long)
    fun setShuffleEnabled(enabled: Boolean)
    fun toggleRepeatMode()
}
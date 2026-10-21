package com.example.service.domain.controller

import com.example.service.domain.model.DynamicPlayBackState
import com.example.core_domain.model.PlayableItem
import com.example.service.domain.model.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface MusicController {
    val playbackState: StateFlow<PlaybackState>
    val dynamicPlayBackState : StateFlow<DynamicPlayBackState>
    fun bind()
    fun unbind()


    fun <T : PlayableItem> appendToQueue(data: List<T>)
    fun <T : PlayableItem> playQueue(data: List<T>, startIndex: Int)
    fun <T : PlayableItem> setQueue(data: List<T>, startIndex: Int)
    fun seekToIndex(index: Int)
    fun playAtIndex(index: Int)
    fun <T: PlayableItem> playPauseSingleItem(item : T)
    fun <T: PlayableItem> playSingleItem(item : T)
    fun togglePlayPause()
    fun next()
    fun previous()
    fun seekTo(positionMs: Long)
    fun setShuffleEnabled(enabled: Boolean)
    fun toggleRepeatMode()
}
package com.example.api.useCases

import com.example.service.controller.MusicController
import com.example.core_data.model.PlayableItem
import javax.inject.Inject

class PlayPauseClickedUseCase @Inject constructor(
    private val musicController: MusicController
) {
    operator fun invoke(
        currentQueueTrackIds: List<String>,
        currentTrackList: List<PlayableItem>,
        currentChosenSong: String?,
        trackId: String
    ) {
        if (currentChosenSong == trackId) {
            musicController.togglePlayPause()
            return
        }
        val index = currentTrackList.indexOfFirst { it.id == trackId }
        if (index == -1) return

        if (currentQueueTrackIds == currentTrackList.map { it.id }) {
            musicController.playAtIndex(index)
        } else {
            musicController.playQueue(currentTrackList, index)
        }
    }
}
package com.example.api.useCases

import com.example.service.domain.controller.MusicController
import com.example.service.domain.model.PlayableItem
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
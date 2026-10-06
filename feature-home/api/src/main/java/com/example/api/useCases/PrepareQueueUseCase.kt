package com.example.api.useCases

import com.example.api.response.TrackResponseDO
import com.example.service.controller.MusicController
import javax.inject.Inject

class PrepareQueueUseCase @Inject constructor(
    private val musicController: MusicController
) {
    operator fun invoke(
        currentQueueTrackIds: List<String>,
        currentChosenSong: String?,
        trackList: List<TrackResponseDO>,
        trackId: String
    ) {
        if (currentChosenSong == trackId) return

        val index = trackList.indexOfFirst { it.id == trackId }
        if (index == -1) return

        if (currentQueueTrackIds == trackList.map { it.id }) {
            musicController.seekToIndex(index)
        } else {
            musicController.setQueue(trackList, index)
        }
    }
}
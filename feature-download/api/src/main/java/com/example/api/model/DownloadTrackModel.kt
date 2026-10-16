package com.example.api.model

import com.example.service.domain.model.PlayableItem
import com.example.storage.data.model.DownloadedTrackView
import com.example.storage.data.model.DownloadingState

data class DownloadTrackModel(
    override val id : String,
    val filePath : String?=null,
    val state : DownloadingState,
    override val name: String,
    val duration: Int,
    val artistId: String,
    override val artistName: String,
    override val audio : String,
    override val image: String
) : PlayableItem

    fun DownloadedTrackView.toUiModel() : DownloadTrackModel =
        DownloadTrackModel(
            id = this.id,
            filePath = this.filePath,
            state = this.state,
            name = this.name,
            duration = this.duration,
            artistId = this.artistId,
            artistName = this.artistName,
            audio = this.audio,
            image = this.image
        )

package com.example.api.mapper

import com.example.core_domain.model.TrackResponseDO
import com.example.storage.data.model.DownloadedMusicEntity

fun DownloadedMusicEntity.toUiModel() : TrackResponseDO =
    TrackResponseDO(
            id = this.id,
            name = this.name,
            duration = this.duration,
            artistId = this.artistId,
            artistName = this.artistName,
            audio = this.audio,
            image = this.image,
            allowedDownloadAudio = true
        )

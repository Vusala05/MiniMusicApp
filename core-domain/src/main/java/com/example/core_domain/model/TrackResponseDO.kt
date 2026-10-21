package com.example.core_domain.model

data class TrackResponseDO(
    override val id: String,
    override val name: String,
    val duration: Int,
    val artistId: String,
    override val artistName: String,
    override val image: String,
    override val audio : String,
    val audioDownload: String?=null,
    val allowedDownloadAudio : Boolean,
) : PlayableItem
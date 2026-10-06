package com.example.api.response

import com.example.service.model.PlayableItem

data class TrackResponseDO(
    override val id: String,
    override val name: String,
    val duration: Int,
    val artistId: String,
    override val artistName: String,
    override val image: String,
    override val audio : String,
    val instruments: List<String>
) : PlayableItem


data class MusicInfoDO(
    val vocalInstrumental: String,
    val lang: String,
    val gender: String,
    val acousticElectric: String,
    val speed: String,
    val tags: MusicTagsDO?
)
data class MusicTagsDO(
    val genres: List<String>,
    val instruments: List<String>,
    val varTags: List<String>
)
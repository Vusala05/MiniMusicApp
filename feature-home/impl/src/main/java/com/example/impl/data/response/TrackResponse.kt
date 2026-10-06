package com.example.impl.data.response

import com.example.api.response.TrackResponseDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TrackResult(
    @SerialName("id")
    val id: String?=null,
    @SerialName("name")
    val name: String?=null,
    @SerialName("duration")
    val duration: Int?=null,
    @SerialName("artist_id")
    val artistId: String?=null,
    @SerialName("artist_name")
    val artistName: String?=null,
    @SerialName("artist_idstr")
    val artistIdStr: String?=null,
    @SerialName("album_name")
    val albumName: String?=null,
    @SerialName("album_id")
    val albumId: String?=null,
    @SerialName("license_ccurl")
    val licenseCcUrl: String?=null,
    @SerialName("position")
    val position: Int?=null,
    @SerialName("releasedate")
    val releaseDate: String?=null,
    @SerialName("album_image")
    val albumImage: String?=null,
    @SerialName("audio")
    val audio: String?=null,
    @SerialName("audiodownload")
    val audioDownload: String?=null,
    @SerialName("prourl")
    val proUrl: String?=null,
    @SerialName("shorturl")
    val shortUrl: String?=null,
    @SerialName("shareurl")
    val shareUrl: String?=null,
    @SerialName("waveform")
    val waveform: String?=null,
    @SerialName("image")
    val image: String?=null,
    @SerialName("musicinfo")
    val musicInfo: MusicInfo?=null,
    @SerialName("audiodownload_allowed")
    val audioDownloadAllowed: Boolean?=null,
    @SerialName("content_id_free")
    val contentIdFree: Boolean?=null
){
    fun toUiModel() : TrackResponseDO {
        return TrackResponseDO(
            id = this.id.orEmpty(),
            name = this.name.orEmpty().ifEmpty { "Unknown Track" },
            duration = this.duration ?:0,
            artistId = this.artistId.orEmpty(),
            artistName = this.artistName.orEmpty().ifEmpty { "Unknown Artist" },
            image = this.image.orEmpty().ifEmpty { this.albumImage.orEmpty() },
            audio = this.audio.orEmpty(),
            instruments = this.musicInfo?.tags?.instruments.orEmpty()
        )
    }


}


@Serializable
data class MusicInfo(
    @SerialName("vocalinstrumental")
    val vocalInstrumental: String?=null,
    @SerialName("lang")
    val lang: String?=null,
    @SerialName("gender")
    val gender: String?=null,
    @SerialName("acousticelectric")
    val acousticElectric: String?=null,
    @SerialName("speed")
    val speed: String?=null,
    @SerialName("tags")
    val tags: MusicTags?=null
)

@Serializable
data class MusicTags(
    @SerialName("genres")
    val genres: List<String>?=null,
    @SerialName("instruments")
    val instruments: List<String>?=null,
    @SerialName("vartags")
    val varTags: List<String>?=null
)
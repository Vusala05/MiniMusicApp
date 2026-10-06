package com.example.impl.data.response

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.api.response.AlbumResponseDO
import com.example.core_data.util.toDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class AlbumResponse(
    @SerialName("id")
    val id : String?,
    @SerialName("name")
    val name : String?,
    @SerialName("releasedate")
    val releaseDate : String?,
    @SerialName("artist_id")
    val artistId : String?,
    @SerialName("artist_name")
    val artistName : String?,
    @SerialName("image")
    val image : String?,
    @SerialName("zip")
    val zip : String?,
    @SerialName("shorturl")
    val shortUrl : String?,
    @SerialName("shareurl")
    val shareUrl : String?,
    @SerialName("zip_allowed")
    val zipAllowed : Boolean?,
    @SerialName("musicinfo")
    val musicInfo: MusicInfoResponse?

    ) {
      @RequiresApi(Build.VERSION_CODES.O)
      fun toUiModel() : AlbumResponseDO {
        val date = LocalDate.parse(this.releaseDate)
        return AlbumResponseDO(
            id = this.id.orEmpty(),
            name = this.name.orEmpty().ifEmpty { "Unknown Album" },
            releaseDate = date,
            artistId = this.artistId.orEmpty(),
            artistName = this.artistName.orEmpty().ifEmpty { "Unknown" },
            image = this.image.orEmpty()
        )
    }


}

@Serializable
data class MusicInfoResponse(
    @SerialName("tags")
    val tags : List<String>?,
    @SerialName("description")
    val description : Map<String, String>?
)
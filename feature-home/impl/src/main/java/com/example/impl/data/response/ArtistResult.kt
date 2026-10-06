package com.example.impl.data.response

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.api.response.ArtistResponseDO
import com.example.core_data.util.toDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistResult(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("website")
    val website: String?,
    @SerialName("joindate")
    val joinDate: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("shorturl")
    val shortUrl: String?,
    @SerialName("shareurl")
    val shareUrl: String?
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun toUiModel() : ArtistResponseDO {
        return ArtistResponseDO(
            id = this.id.orEmpty(),
            name = this.name.orEmpty().ifEmpty {"Unknown Artist"},
            joinDate = this.joinDate?.toDate() ?:"00.00.0000",
            image = this.image.orEmpty()
        )
    }

}
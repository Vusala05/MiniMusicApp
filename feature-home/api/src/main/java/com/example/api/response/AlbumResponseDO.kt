package com.example.api.response

import java.time.LocalDate

data class AlbumResponseDO(
    val id : String,
    val name : String,
    val releaseDate : LocalDate,
    val artistId : String,
    val artistName : String,
    val image : String,
) {
}


package com.example.core_data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "downloaded_musics")
data class DownloadedMusicEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val duration: Int,
    val artistId: String,
    val artistName: String,
    val audio : String,
    val image: String,
)
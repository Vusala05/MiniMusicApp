package com.example.storage.data.model

import androidx.room3.DatabaseView

@DatabaseView(viewName = "downloaded_track_view",
    value = """
        SELECT d.id AS id,
               d.state AS state,
               d.filePath AS filePath,
               m.name AS name,
               m.duration AS duration,
               m.artistId AS artistId,
               m.artistName AS artistName,
               m.audio AS audio,
               m.image AS image
        FROM downloaded d
        INNER JOIN downloaded_musics m ON d.id = m.id
    """)
data class DownloadedTrackView(
    val id : String,
    val filePath : String?=null,
    val state : DownloadingState,
    val name: String,
    val duration: Int,
    val artistId: String,
    val artistName: String,
    val audio : String,
    val image: String
)

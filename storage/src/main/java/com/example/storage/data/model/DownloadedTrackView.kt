package com.example.storage.data.model

import androidx.room3.DatabaseView

// bu sekilde yazmaq ele de vacib deyildi, sonradan ferqine vardim, yeni ki bir basa ele dao da join ile
//yaza bilerdim @Query seklinde, amma bu sekilde 1 defelik bir query oldugundan, her defe eyni join yazmayim deye
//saxladimki lazim ola biler sonradan
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

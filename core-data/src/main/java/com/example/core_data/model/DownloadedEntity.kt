package com.example.core_data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "downloaded")
data class DownloadedEntity(
    @PrimaryKey
    val id : String,
    val filePath : String?=null,
    val state : DownloadingState
)
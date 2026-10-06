package com.example.core_data.model

import kotlinx.serialization.SerialName

data class ErrorModelDo(
    val code : Int?,
    val errorMessage : String?,
    val warnings : String?
)
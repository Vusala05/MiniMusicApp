package com.example.core_domain.model

data class ErrorModelDo(
    val code : Int?,
    val errorMessage : String?,
    val warnings : String?
)
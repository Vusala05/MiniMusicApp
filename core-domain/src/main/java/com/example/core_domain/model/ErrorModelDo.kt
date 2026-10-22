package com.example.core_domain.model

data class ErrorModelDo(
    val errorType: ErrorType,
    val errorMessage : String?,
    val warnings : String?
)
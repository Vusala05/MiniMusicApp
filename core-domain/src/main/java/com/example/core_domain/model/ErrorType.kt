package com.example.core_domain.model

enum class ErrorType {
     NO_INTERNET,
     TIMEOUT,
     SERVER,
     IO,
     API_ERROR,
     FILE,
     UNAUTHORIZED,
     MAPPING,
     RATE_LIMIT,
     UNKNOWN,
     SSL,
     HTTP
}
fun fromHttpCode(code : Int) : ErrorType{
    return when(code){
        401, 403 -> ErrorType.UNAUTHORIZED
        404 -> ErrorType.UNAUTHORIZED
        429 -> ErrorType.RATE_LIMIT
        in 500..599 -> ErrorType.SERVER
        else -> ErrorType.HTTP
    }
}
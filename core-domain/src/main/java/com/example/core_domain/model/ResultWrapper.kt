package com.example.core_domain.model

sealed interface ResultWrapper<out T> {
    data class Success<T>(val data : T) : ResultWrapper<T>
    data class Error (val appError: AppError) : ResultWrapper<Nothing>
}

fun <T,R> ResultWrapper<T>.toTransform( transform:(T) -> R) : ResultWrapper<R>{
    return when(this){
        is ResultWrapper.Error -> ResultWrapper.Error(appError)
        is ResultWrapper.Success ->{
            try {
                val transformResult = transform(data)
                ResultWrapper.Success(data = transformResult)
            } catch (e : Exception){
                ResultWrapper.Error(
                    AppError.SystemError(
                        errorModel = ErrorModelDo(
                            errorType = ErrorType.MAPPING,
                            errorMessage = e.localizedMessage,
                            warnings = e.localizedMessage
                        ),
                        exception = e
                    )
                )
            }

        }

    }
}

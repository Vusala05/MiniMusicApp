package com.example.core_data.model

sealed interface ResultWrapper<out T> {
    data class Success<T>(val data : T) : ResultWrapper<T>
    data class Error (val appError: AppError) : ResultWrapper<Nothing>
}

inline fun <reified T,reified R> handleResultWrapper(
    result : ResultWrapper<T>,
    transform : (T) -> R
) : ResultWrapper<R>{
     return when(result){
         is ResultWrapper.Error ->{
             ResultWrapper.Error(result.appError)
         }
         is ResultWrapper.Success ->{
             try {
                 val transformResult = transform(result.data)
                 ResultWrapper.Success(data = transformResult)
             } catch (e : Exception){
                 ResultWrapper.Error(
                     AppError.SystemError(
                         errorModel = ErrorModelDo(
                             code = Integer.MAX_VALUE - 4,
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

package com.example.core_data.util

import com.example.core_data.model.BaseResponse
import com.example.core_domain.model.AppError
import com.example.core_domain.model.ErrorModelDo
import com.example.core_domain.model.ErrorType
import com.example.core_domain.model.fromHttpCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

fun Throwable.toSystemError() : AppError.SystemError{
    val type = when(this){
        is UnknownHostException, is ConnectException -> ErrorType.NO_INTERNET
        is SocketTimeoutException -> ErrorType.TIMEOUT
        is SSLException -> ErrorType.SSL
        is IOException -> ErrorType.IO
        else -> ErrorType.UNKNOWN
    }
    return AppError.SystemError(
        errorModel = ErrorModelDo (
            errorType = type,
            errorMessage = message,
            warnings = null
        ),
        exception = this as Exception
    )
}

fun <T> Response<T>.toBusinessError() : AppError.BusinessError{

    val errorBody = errorBody()?.string()
        val parsedError = errorBody?.takeIf { it.isNotBlank()}?.let {
            try {
                Json.decodeFromString<BaseResponse<JsonElement>>(it).headers
                    .toErrorModel().copy(errorType = fromHttpCode(code()))
            } catch (e : Exception){
                null
            }
        }
    val defaultErrorModel = ErrorModelDo(
        errorType = fromHttpCode(code()),
        errorMessage = "System error occurred ${code()}",
        warnings = null)

      val appError =
          if (parsedError!=null) {
          AppError.BusinessError(
              errorModel = ErrorModelDo(
                  errorType = parsedError.errorType,
                  errorMessage = parsedError.errorMessage,
                  warnings = parsedError.warnings
              ) )
      } else{
              AppError.BusinessError(
                  defaultErrorModel
              )
      }
    return appError

}
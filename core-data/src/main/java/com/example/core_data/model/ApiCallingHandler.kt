package com.example.core_data.model

import com.example.core_data.feauture.GlobalNetworkHandler
import kotlinx.serialization.json.Json
import okio.IOException
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

inline fun <reified T > apiCallingHandler(
    globalNetworkHandler: GlobalNetworkHandler,
    apiCalling : () -> Response<BaseResponse<T>>
) : ResultWrapper<T?> {
    return try {
    val result = apiCalling()

    if (result.isSuccessful) {
        val data = result.body()?.results

        ResultWrapper.Success(data = data)


    } else {
        val errorBody = result.errorBody()?.string()
        val errorModel = if (errorBody.isNullOrBlank()) {
            ErrorModelDo(
                code = result.code(),
                errorMessage = result.message(),
                warnings = "Something went wrong"
            )
        } else {
            try {
                Json.decodeFromString<HeaderResponse>(errorBody).toUiModel()
            } catch (e: Exception) {
                ErrorModelDo(
                    code = Integer.MAX_VALUE - 1,
                    errorMessage = e.localizedMessage,
                    warnings = e.localizedMessage
                )


            }
        }

        globalNetworkHandler.globalErrorHandler(
            error = AppError.BusinessError(
                errorModel = errorModel,
                exception = Exception()
            )
        )
        ResultWrapper.Error(
            appError = AppError.BusinessError(
                errorModel = errorModel,
                exception = Exception()
            )
        )

    }
} catch (throwable : Throwable){
            when (throwable) {
                is UnknownHostException,
                is SSLHandshakeException,
                is SocketTimeoutException,
                is SocketException -> {
                    val error = ResultWrapper.Error(
                        appError = AppError.SystemError(
                            errorModel = ErrorModelDo(
                                errorMessage = throwable.message,
                                code = Integer.MAX_VALUE - 2,
                                warnings = throwable.message
                            ),
                            exception = throwable
                        )

                    )
                    globalNetworkHandler.globalErrorHandler(
                        error = AppError.SystemError(
                            errorModel = ErrorModelDo(
                                errorMessage = throwable.message,
                                code = Integer.MAX_VALUE - 2,
                                warnings = throwable.message
                            ),
                            exception = throwable
                        )
                    )
                    return error
                }

                is IOException -> {
                    val error = ResultWrapper.Error(
                        appError = AppError.SystemError(
                            errorModel = ErrorModelDo(
                                errorMessage = throwable.message,
                                code = Integer.MAX_VALUE - 3,
                                warnings = throwable.message
                            ),
                            exception = throwable
                        )

                    )
                    globalNetworkHandler.globalErrorHandler(
                        error = AppError.SystemError(
                            errorModel = ErrorModelDo(
                                errorMessage = throwable.message,
                                code = Integer.MAX_VALUE - 2,
                                warnings = throwable.message
                            ),
                            exception = throwable
                        )
                    )
                    return error
                }
                else -> {
                    val error = ResultWrapper.Error(
                        appError = AppError.SystemError(
                            errorModel = ErrorModelDo(
                                errorMessage = SOMETHING_WENT_WRONG,
                                code = Integer.MAX_VALUE,
                                warnings = SOMETHING_WENT_WRONG
                            ),
                            exception = Exception(throwable.message, throwable)
                        )

                    )
                    globalNetworkHandler.globalErrorHandler(
                        error = AppError.SystemError(
                            errorModel = ErrorModelDo(
                                errorMessage = SOMETHING_WENT_WRONG,
                                code = Integer.MAX_VALUE,
                                warnings = SOMETHING_WENT_WRONG
                            ),
                            exception = Exception(throwable.message, throwable)
                        )
                    )
                    return error
                }
            }

        }


}
const val SOMETHING_WENT_WRONG = "Something went wrong"
const val EMPTY_BODY = "The List is empty"


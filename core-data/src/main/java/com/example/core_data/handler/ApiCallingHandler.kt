package com.example.core_data.handler

import android.util.Log
import com.example.core_data.model.BaseResponse
import com.example.core_data.model.ErrorResponse
import com.example.core_data.model.HeaderResponse
import com.example.core_data.util.toBusinessError
import com.example.core_data.util.toSystemError
import com.example.core_domain.model.ResultWrapper
import com.example.core_domain.handler.GlobalNetworkHandler
import com.example.core_domain.model.AppError
import com.example.core_domain.model.ErrorModelDo
import com.example.core_domain.model.ErrorType
import com.example.core_domain.model.fromHttpCode
import kotlinx.serialization.json.Json
import okio.IOException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLHandshakeException
import kotlin.coroutines.cancellation.CancellationException

inline fun <T> apiCallingHandler(
    globalNetworkHandler: GlobalNetworkHandler,
    apiCalling: () -> Response<BaseResponse<T>>
): ResultWrapper<T?> {
    return try {
        val response = apiCalling()
        if(response.isSuccessful){
            ResultWrapper.Success(data = response.body()?.results )
        } else{
            ResultWrapper.Error(response.toBusinessError())
        }

   } catch (e : Exception){
        Log.d("ApiCall", "failed", Throwable("caller trace"))
        globalNetworkHandler.globalErrorHandler(
            e.toSystemError()
        )
        ResultWrapper.Error(e.toSystemError())
   }
}


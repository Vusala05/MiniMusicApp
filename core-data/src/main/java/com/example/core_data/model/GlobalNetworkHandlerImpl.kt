package com.example.core_data.model

import com.example.core_data.feauture.GlobalNetworkHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GlobalNetworkHandlerImpl @Inject constructor(val coroutineScope: CoroutineScope) : GlobalNetworkHandler {
    val _sharedFlow  = MutableSharedFlow<AppError>()
    val sharedFlow  = _sharedFlow.asSharedFlow()
    override fun globalErrorHandler(error: AppError) {
        coroutineScope.launch {
            if(error !is CancellationException) {
                _sharedFlow.emit(error)
            }
        }


    }
}
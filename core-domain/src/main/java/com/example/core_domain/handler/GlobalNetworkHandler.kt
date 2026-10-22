package com.example.core_domain.handler

import com.example.core_domain.model.AppError
import kotlinx.coroutines.flow.SharedFlow

interface GlobalNetworkHandler {

   fun globalErrorHandler(error : AppError)
   val sharedFlow : SharedFlow<AppError>
}
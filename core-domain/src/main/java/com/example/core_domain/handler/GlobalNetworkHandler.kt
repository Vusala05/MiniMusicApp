package com.example.core_domain.handler

import com.example.core_domain.model.AppError

interface GlobalNetworkHandler {

   fun globalErrorHandler(error : AppError)
}
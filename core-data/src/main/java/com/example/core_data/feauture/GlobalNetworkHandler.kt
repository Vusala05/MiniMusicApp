package com.example.core_data.feauture

import com.example.core_data.model.AppError

interface GlobalNetworkHandler {

   fun globalErrorHandler(error : AppError)
}
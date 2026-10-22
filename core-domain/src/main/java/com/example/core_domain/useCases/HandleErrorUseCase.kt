package com.example.core_domain.useCases

import com.example.core_domain.model.AppError
import com.example.core_domain.model.ErrorType
import javax.inject.Inject

class HandleErrorUseCase @Inject constructor(){

    operator fun invoke (appError: AppError) : String{
        val model =  when(appError){
            is AppError.SystemError -> { appError.errorModel}

            is AppError.BusinessError -> { appError.errorModel }
        }
        return when(model.errorType){
            ErrorType.NO_INTERNET -> "No internet connection. Please check your network."
            ErrorType.TIMEOUT -> "Connection is slow. Please try again."
            ErrorType.SSL, ErrorType.IO -> "Network error. Please try again."
            ErrorType.SERVER -> "Server is unavailable. Try again later."
            ErrorType.RATE_LIMIT -> "Too many requests. Please wait a moment."
            ErrorType.UNAUTHORIZED -> "Access denied. Please try again later."
            ErrorType.FILE -> "Couldn't save the file. Check your storage."
            ErrorType.API_ERROR, ErrorType.HTTP, ErrorType.MAPPING -> model.errorMessage ?: SOMETHING_WENT_WRONG
            ErrorType.UNKNOWN -> SOMETHING_WENT_WRONG
        }
    }
    companion object{
        const val SOMETHING_WENT_WRONG = "Something went wrong"
    }

}
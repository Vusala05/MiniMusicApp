package com.example.core_data.useCases

import com.example.core_data.model.AppError
import javax.inject.Inject

class HandleErrorUseCase @Inject constructor(){

    operator fun invoke (appError: AppError) : String{
        return when(appError){
            is AppError.SystemError -> {
                appError.errorModel.errorMessage ?:"Unknown System Error"
            }

            is AppError.BusinessError -> {
                appError.errorModel.errorMessage ?:"Unknown Service Error"
            }
        }
    }
}
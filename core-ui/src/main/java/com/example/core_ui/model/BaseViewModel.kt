package com.example.core_ui.model

import androidx.lifecycle.ViewModel
import com.example.core_domain.model.AppError
import com.example.core_domain.useCases.HandleErrorUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S,E>(
    val initialState : S,
    val handleErrorUseCase: HandleErrorUseCase
) : ViewModel() {

    val _state = MutableStateFlow(initialState)
    val state= _state.asStateFlow()

    val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    protected fun getCurrentState() : S = _state.value

    protected fun updateUiState(update : (S) -> S){
        _state.update(update)
    }

    protected suspend fun sendEffect(value : E) {
        _effect.emit(value)
    }

    protected suspend fun handleError(appError: AppError) {
      when(appError){
          is AppError.BusinessError ->{
              val mappedError = handleErrorUseCase(appError)
              showErrorMessage(mappedError)
          }
          is AppError.SystemError -> {
              //Skip
          }

      }
    }
     protected open  suspend fun showErrorMessage(error : String) {
     }


}
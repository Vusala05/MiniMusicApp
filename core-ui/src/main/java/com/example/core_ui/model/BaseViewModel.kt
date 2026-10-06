package com.example.core_ui.model

import androidx.lifecycle.ViewModel
import com.example.core_data.model.AppError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel<S,E>(
    val initialState : S
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

    protected fun handleError(appError: AppError) {

    }



}
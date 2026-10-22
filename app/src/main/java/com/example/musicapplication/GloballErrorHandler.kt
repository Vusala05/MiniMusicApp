package com.example.musicapplication

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.core_domain.handler.GlobalNetworkHandler
import com.example.core_domain.useCases.HandleErrorUseCase
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Singleton

@Singleton
@Composable
fun GlobalErrorHandler(
    globalNetworkHandler: GlobalNetworkHandler,
    handleErrorUseCase: HandleErrorUseCase
){
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        globalNetworkHandler.sharedFlow.collectLatest {
            Toast.makeText(context,handleErrorUseCase(it), Toast.LENGTH_SHORT).show()
        }
    }

}
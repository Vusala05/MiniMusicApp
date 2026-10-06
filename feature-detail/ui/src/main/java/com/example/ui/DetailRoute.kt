package com.example.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailRoute(
    musicId : String?=null
){
    val context = LocalContext.current
    val viewModel : DetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect
    DetailScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
    LaunchedEffect(musicId) {
        viewModel.handleIntent(DetailContract.Intent.LoadDetailScreen(musicId ?: ""))
    }

    LaunchedEffect(effect) {
        effect.collectLatest {
            when(it){
                is DetailContract.Effect.SendMessage -> {
                    Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

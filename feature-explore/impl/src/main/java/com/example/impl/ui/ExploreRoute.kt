package com.example.impl.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExploreRoute(){

    val viewModel : ExploreViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect
    val context = LocalContext.current
    ExploreScreen (
        state = state,
        onIntent = viewModel::handleIntent
    )
    LaunchedEffect(effect) {
        effect.collectLatest {
            when(it){
                is ExploreContract.Effect.SendMessage -> {
                    Toast.makeText(context,it.data, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


}
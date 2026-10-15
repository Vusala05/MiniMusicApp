package com.example.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SavedRoute() {

    val viewModel : SavedViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    SavedScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )

}
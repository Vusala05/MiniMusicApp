package com.example.ui.SeeAllScreen.SeeAllTrack

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SeeAllTrackRoute(){

     val viewModel : SeeAllTrackViewModel = hiltViewModel()
     val state by viewModel.state.collectAsStateWithLifecycle()
     val effect = viewModel.effect
     val context = LocalContext.current
     SeeAllScreen(
          state = state,
          onIntent = viewModel::handleIntent
     )
     LaunchedEffect(effect) {
          effect.collectLatest {
               when(it){
                    is SeeAllTrackContract.Effect.ShowMessage -> {
                         Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()

                    }
               }
          }
     }

}

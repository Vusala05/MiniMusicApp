package com.example.ui.SeeAllScreen.SeeAllAlbum

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeeAllAlbumRoute(
    navigateTrackList : (String)-> Unit
){
    val viewModel : SeeAllAlbumViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect
    val context = LocalContext.current
   SeeAllAlbumScreen(
       state = state,
       onIntent = viewModel::handleIntent
   ) {
       navigateTrackList(it)
   }


    LaunchedEffect(effect) {
        effect.collectLatest {
           when(it){
                is SeeAllAlbumContract.Effect.ShowMessage -> {
                    Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
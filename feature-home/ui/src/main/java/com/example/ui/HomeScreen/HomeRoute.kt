package com.example.ui.HomeScreen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ui.HomeScreen.HomeViewModel
import com.example.ui.SeeALType
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeRoute(
    onNavigateSeeAllTrack: (SeeALType, String?, String?)-> Unit,
    onNavigateSeeAllAlbum: (String?)-> Unit
) {
    val viewModel : HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect
    val context = LocalContext.current
    HomeScreen(
        state = state,
        onIntent = viewModel::handleIntent,
        onNavigateSeeAllTrack = { type,tag,albumId->
            onNavigateSeeAllTrack(type,tag,albumId)
        },
        onNavigateSeeAllAlbum = {
            onNavigateSeeAllAlbum(it)
        }
    )

    LaunchedEffect(effect) {
        effect.collectLatest {
            when(it){
             is HomeContract.Effect.SendMessage -> {
                 Toast.makeText(context,it.data, Toast.LENGTH_SHORT).show()
             }
            }
        }
    }
}
package com.example.musicapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.NotificationPermissionHandler
import com.example.musicapplication.ui.theme.MusicApplicationTheme
import com.example.navigation.Navigator
import com.example.service.MusicController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var musicController: MusicController

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NotificationPermissionHandler()
            MusicApplicationTheme {
             App(navigator = navigator, navController = navController)
            }
        }


    }

    override fun onStart() {
        musicController.bind()
        super.onStart()
    }

    override fun onStop() {
        musicController.unbind()
        super.onStop()
    }


}


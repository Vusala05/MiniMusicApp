package com.example.musicapplication

import android.app.Application
import com.example.service.service.MediaSessionController
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
@Inject
lateinit var mediaSessionController : MediaSessionController
    override fun onCreate() {
        super.onCreate()
            //mediaSessionController.bind()


    }
}
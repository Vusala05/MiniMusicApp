package com.example.service.di

import com.example.service.controller.MusicController
import com.example.service.service.MediaSessionController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicControllerBinder {

    @Binds
    @Singleton
    abstract fun bindMusicController(musicServiceConnector: MediaSessionController) : MusicController
}
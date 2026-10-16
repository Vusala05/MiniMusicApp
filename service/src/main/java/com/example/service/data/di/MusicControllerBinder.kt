package com.example.service.data.di

import com.example.service.domain.controller.MusicController
import com.example.service.data.service.MediaSessionController
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
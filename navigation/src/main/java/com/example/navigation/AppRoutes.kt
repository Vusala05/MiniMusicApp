package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoutes {

    @Serializable
    data object Home : AppRoutes

    @Serializable
    data object Explore : AppRoutes

    @Serializable
    data object Saved : AppRoutes

    @Serializable
    data class SeeAllTrack(val tag : String?, val albumId : String?): AppRoutes
    @Serializable
    data class SeeAllAlbum(val tag : String?=null): AppRoutes
    @Serializable
    data class Detail (val id : String ) : AppRoutes
  companion object{
      const val HOME = "musicapp.az://home"
      const val EXPLORE = "musicapp.az://explore"
      const val MUSIC_DETAIL  = "musicapp.az://detail"
      const val SAVED = "musicapp.az://saved"
  }
}
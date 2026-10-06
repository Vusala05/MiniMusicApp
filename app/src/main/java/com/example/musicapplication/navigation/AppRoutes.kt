package com.example.musicapplication.navigation

import com.example.ui.SeeALType
import kotlinx.serialization.Serializable

sealed interface AppRoutes {

    @Serializable
    data object Home : AppRoutes

    @Serializable
    data object Explore : AppRoutes

    @Serializable
    data object Saved : AppRoutes

    @Serializable
    data class SeeAllTrack(val tag : String?, val albumId : String?, val seeALType: SeeALType): AppRoutes
    @Serializable
    data class SeeAllAlbum(val type : String?=null): AppRoutes
    @Serializable
    data class Detail (val id : String ) : AppRoutes
}
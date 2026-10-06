package com.example.navigation

sealed class DeeplinkNavigator(val routeLink : String) {

    data object MusicHome : DeeplinkNavigator(HOME)
    data object ExploreMusic : DeeplinkNavigator(EXPLORE)
    data class DetailMusic(val argument : String? ) : DeeplinkNavigator(MUSIC_DETAIL)
    data object SavedMusic : DeeplinkNavigator(SAVED)


    companion object {
        const val HOME = "musicapp.az://home"
        const val EXPLORE = "musicapp.az://explore"
        const val MUSIC_DETAIL  = "musicapp.az://detail/"
        const val SAVED = "musicapp.az://saved"
    }
}



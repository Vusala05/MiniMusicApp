package com.example.musicapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.navigation.AppRoutes
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Navigator
import com.example.ui.DetailRoute
import com.example.ui.ExploreRoute
import com.example.ui.HomeScreen.HomeRoute
import com.example.ui.SeeAllScreen.SeeAllAlbum.SeeAllAlbumRoute
import com.example.ui.SeeAllScreen.SeeAllTrack.SeeAllTrackRoute

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun  MainRoutes(
    navHostController: NavHostController,
    navigator: Navigator,
    paddingValues: PaddingValues
) {
    LaunchedEffect(navHostController) {
        navigator.navController = navHostController

    }

    NavHost(
        navHostController,
        startDestination = AppRoutes.Home
    )
    {
        composable<AppRoutes.Home>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.MusicHome.routeLink })
        ){
            HomeRoute()
        }


        composable<AppRoutes.Explore>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.ExploreMusic.routeLink })
        ){
            ExploreRoute()
        }

        composable<AppRoutes.Detail>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "${DeeplinkNavigator.MUSIC_DETAIL}{id}" })
        ){ navStackEntry ->
            val args = navStackEntry.toRoute<AppRoutes.Detail>()
            DetailRoute( musicId = args.id)
        }

        composable<AppRoutes.Saved>(
            deepLinks = listOf(
                navDeepLink { uriPattern = DeeplinkNavigator.SavedMusic.routeLink })
        ){
        }
        composable<AppRoutes.SeeAllTrack> { navStackEntry ->
            SeeAllTrackRoute()
        }

        composable<AppRoutes.SeeAllAlbum> {
            SeeAllAlbumRoute()
        }



    }
}
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
import com.example.navigation.Navigator
import com.example.impl.ui.DetailRoute
import com.example.impl.ui.ExploreRoute
import com.example.impl.ui.HomeScreen.HomeRoute
import com.example.impl.ui.SavedRoute
import com.example.impl.ui.SeeAllScreen.SeeAllAlbum.SeeAllAlbumRoute
import com.example.impl.ui.SeeAllScreen.SeeAllTrack.SeeAllTrackRoute

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
                navDeepLink<AppRoutes.Home>(basePath = AppRoutes.HOME)
            )
        ){
            HomeRoute()
        }


        composable<AppRoutes.Explore>(
            deepLinks = listOf(
                navDeepLink<AppRoutes.Explore>(basePath = AppRoutes.EXPLORE)
        )
        ){
            ExploreRoute()
        }

        composable<AppRoutes.Detail>(
            deepLinks = listOf(
                navDeepLink<AppRoutes.Detail>(basePath = AppRoutes.MUSIC_DETAIL)
            )
        ){ navStackEntry ->
            val args = navStackEntry.toRoute<AppRoutes.Detail>()
            DetailRoute( musicId = args.id)
        }

        composable<AppRoutes.Saved>(
            deepLinks = listOf(
                navDeepLink<AppRoutes.Saved>(basePath = AppRoutes.SAVED)
            )
        ){
            SavedRoute()
        }
        composable<AppRoutes.SeeAllTrack> {
            SeeAllTrackRoute()
        }

        composable<AppRoutes.SeeAllAlbum> {
            SeeAllAlbumRoute()
        }



    }

}


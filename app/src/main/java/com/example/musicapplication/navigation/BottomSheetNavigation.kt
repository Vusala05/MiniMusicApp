package com.example.musicapplication.navigation

import com.example.core_ui.R
import com.example.navigation.AppRoutes

data class BottomSheetNavigation(
    val route: AppRoutes,
    val icon : Int
) {
    companion object{
        val navigationItems = listOf(
            BottomSheetNavigation(
             route = AppRoutes.Home,
                icon = R.drawable.home
            ),
            BottomSheetNavigation(
                route = AppRoutes.Explore,
                icon = R.drawable.search
            ),
            BottomSheetNavigation(
                route = AppRoutes.Saved,
                icon = R.drawable.saved
            )
        )
    }
}
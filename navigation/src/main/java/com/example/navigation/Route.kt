package com.example.navigation

sealed interface Route {
    data class IsDeepLinkNavigator(val routeLink : DeeplinkNavigator) : Route
}
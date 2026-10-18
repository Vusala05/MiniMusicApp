package com.example.navigation

import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator @Inject constructor() {
    var navController: NavController? = null
}

    fun Navigator.navigatorRoute(block: NavController.() -> Unit) {
        navController?.block()
    }


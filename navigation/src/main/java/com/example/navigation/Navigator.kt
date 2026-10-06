package com.example.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator @Inject constructor() {
    var navController : NavController? = null
        fun navDeepLink(deeplinkNavigator: DeeplinkNavigator) : Uri {
            return when(deeplinkNavigator){
                is DeeplinkNavigator.DetailMusic -> {
                    (deeplinkNavigator.routeLink + deeplinkNavigator.argument).toUri()
                }

                else -> {
                    (deeplinkNavigator.routeLink).toUri()
                }
            }


        }

    }
    fun Navigator.navigatorDeepLink (route : Route, block : NavController.(destination : Uri )-> Unit){
        val result =  when(route){
            is Route.IsDeepLinkNavigator -> navDeepLink(route.routeLink)
        }
        navController?.block(result)
    }
     fun Navigator.navigation(block : NavController.() -> Unit){
         navController?.block()
     }

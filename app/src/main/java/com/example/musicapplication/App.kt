package com.example.musicapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicapplication.navigation.AppRoutes
import com.example.musicapplication.navigation.BottomSheetNavigation
import com.example.musicapplication.navigation.MainRoutes
import com.example.navigation.Navigator
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(navController : NavHostController, navigator : Navigator) {


    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

   val shouldHiddenNavigationBar = currentBackStackEntry?.destination?.let {
       it.hasRoute<AppRoutes.Detail>() ||
       it.hasRoute<AppRoutes.SeeAllTrack>() ||
       it.hasRoute<AppRoutes.SeeAllAlbum>()
   } ?:false

    Scaffold(
        bottomBar = {
            if (!shouldHiddenNavigationBar) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .shadow(
                                elevation = 16.dp,
                                shape = CircleShape,
                                spotColor = Color(0xFF80154B).copy(alpha = 0.3f),
                                ambientColor = Color.Black
                            )
                            .clip(CircleShape)
                            .background(
                                Color(0xFF1E1B2E).copy(alpha = 0.75f)
                            )
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        Color.White.copy(alpha = 0.05f)
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomSheetNavigation.navigationItems.forEach { item ->
                            val isSelected = currentRoute == item.route::class.qualifiedName

                            val animatedIconTint by animateColorAsState(
                                targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.4f),
                                animationSpec = spring(stiffness = Spring.StiffnessLow),
                                label = "IconTint"
                            )

                            val animatedIndicatorColor by animateColorAsState(
                                targetValue = if (isSelected) Color(0xFF80154B).copy(alpha = 0.8f) else Color.Transparent,
                                animationSpec = spring(stiffness = Spring.StiffnessLow),
                                label = "IndicatorColor"
                            )

                            // Hər bir Navigation Item
                            Box(
                                modifier = Modifier
                                    .height(44.dp)
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(animatedIndicatorColor)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        if (!isSelected) {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = null,
                                    tint = animatedIconTint,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    ) { innerPadding ->
        MainRoutes(
            paddingValues = innerPadding,
            navHostController = navController,
            navigator = navigator
        )
    }


}
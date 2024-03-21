package com.layon.navigationcompose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.layon.quicksvideointeractionandroidapp.ui.screen.CameraScreen
import com.layon.quicksvideointeractionandroidapp.ui.screen.GalleryScreen
import com.layon.quicksvideointeractionandroidapp.ui.screen.HomeScreen
import com.layon.quicksvideointeractionandroidapp.ui.screen.InboxScreen
import com.layon.quicksvideointeractionandroidapp.ui.screen.ProfileScreen
import com.layon.quicksvideointeractionandroidapp.ui.util.textfield.ExpandableInputSearchField

/**
 * This composable function setup the root navigation bar screen for the main app
 * To add new Screen see NavItem.kt
 * The bottomBar is the composable os the navigation bar buttons
 * And NavHost in lambda function is the main screen content of each NavItem
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black,
                modifier = Modifier.height(70.dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(modifier = Modifier.align(Alignment.Bottom), text = navItem.label, color = Color.LightGray, fontSize = 8.sp)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name) {
                HomeScreen()
            }
            composable(route = Screens.InboxScreen.name) {
                InboxScreen()
            }
            composable(route = Screens.CameraScreen.name) {
                CameraScreen()
            }
            composable(route = Screens.GalleryScreen.name) {
                GalleryScreen()
            }
            composable(route = Screens.ProfileScreen.name) {
                ProfileScreen()
            }
        }
    }

}
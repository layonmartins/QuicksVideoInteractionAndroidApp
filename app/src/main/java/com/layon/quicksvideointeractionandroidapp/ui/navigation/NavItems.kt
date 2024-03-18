package com.layon.navigationcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    var label: String,
    var icon: ImageVector,
    val route: String
)

var listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Inbox",
        icon = Icons.Default.Inbox,
        route = Screens.InboxScreen.name
    ),
    NavItem(
        label = "Gallery",
        icon = Icons.Default.GridView,
        route = Screens.GalleryScreen.name
    ),
    NavItem(
        label = "Camera",
        icon = Icons.Default.CameraAlt,
        route = Screens.CameraScreen.name
    ),
    NavItem(
        label = "Profile",
        icon = Icons.Default.Person,
        route = Screens.ProfileScreen.name
    )
)
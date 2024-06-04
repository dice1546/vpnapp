package com.turboguardvpn.vpn.data

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badges: Int,
)



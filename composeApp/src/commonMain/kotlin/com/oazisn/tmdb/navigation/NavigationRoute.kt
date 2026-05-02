package com.oazisn.tmdb.navigation

sealed class NavigationRoute {
    data object Splash : NavigationRoute()
    data object Login : NavigationRoute()
    data object Main : NavigationRoute()
}

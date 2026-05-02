package com.oazisn.tmdb.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavigationManager : ViewModel() {
    val currentRoute = mutableStateOf<NavigationRoute>(NavigationRoute.Splash)

    fun navigate(route: NavigationRoute) {
        currentRoute.value = route
    }
}

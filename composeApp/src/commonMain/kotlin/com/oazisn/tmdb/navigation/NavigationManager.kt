package com.oazisn.tmdb.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavigationManager : ViewModel() {
    private val backStack = mutableListOf<NavigationRoute>(NavigationRoute.Splash)
    val currentRoute = mutableStateOf<NavigationRoute>(NavigationRoute.Splash)

    fun navigate(route: NavigationRoute) {
        backStack.add(route)
        currentRoute.value = route
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeLast()
            currentRoute.value = backStack.last()
        }
    }
}

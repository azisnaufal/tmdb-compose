package com.oazisn.tmdb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oazisn.tmdb.screen.login.LoginScreen
import com.oazisn.tmdb.screen.login.LoginViewModel
import com.oazisn.tmdb.screen.main.MainScreen
import com.oazisn.tmdb.screen.splash.SplashScreen

@Composable
fun NavigationHost(
    navigator: NavigationManager = viewModel { NavigationManager() }
) {
    val currentRoute by navigator.currentRoute

    when (currentRoute) {
        is NavigationRoute.Splash -> {
            SplashScreen(
                onNavigateToLogin = { navigator.navigate(NavigationRoute.Login) }
            )
        }
        is NavigationRoute.Login -> {
            val loginViewModel: LoginViewModel = viewModel { LoginViewModel() }
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { navigator.navigate(NavigationRoute.Main) }
            )
        }
        is NavigationRoute.Main -> {
            MainScreen()
        }
    }
}

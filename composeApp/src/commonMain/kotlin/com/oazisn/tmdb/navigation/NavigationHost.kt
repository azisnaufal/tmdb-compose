package com.oazisn.tmdb.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oazisn.tmdb.screen.detail.DetailScreen
import com.oazisn.tmdb.screen.login.LoginScreen
import com.oazisn.tmdb.screen.login.LoginViewModel
import com.oazisn.tmdb.screen.main.MainScreen
import com.oazisn.tmdb.screen.main.MainViewModel
import com.oazisn.tmdb.screen.splash.SplashScreen

@Composable
fun NavigationHost(
    navigator: NavigationManager = viewModel { NavigationManager() }
) {
    val currentRoute by navigator.currentRoute

    AnimatedContent(
        targetState = currentRoute,
        transitionSpec = {
            fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) togetherWith
                    fadeOut(animationSpec = androidx.compose.animation.core.tween(300))
        }
    ) { route ->
        when (route) {
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
                MainScreen(
                    onMovieClick = { movie -> navigator.navigate(movie.toRoute()) }
                )
            }
            is NavigationRoute.Detail -> {
                DetailScreen(
                    movie = route.toMovie(),
                    onBack = { navigator.navigateBack() }
                )
            }
        }
    }
}

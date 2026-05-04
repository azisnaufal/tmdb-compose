package com.oazisn.tmdb.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.oazisn.tmdb.screen.detail.DetailScreen
import com.oazisn.tmdb.screen.login.LoginScreen
import com.oazisn.tmdb.screen.login.LoginViewModel
import com.oazisn.tmdb.screen.main.MainScreen
import com.oazisn.tmdb.screen.splash.SplashScreen

@Composable
fun NavigationHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Splash,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable<Splash> {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo<Splash> { inclusive = true }
                    }
                }
            )
        }
        composable<Login> {
            val loginViewModel: LoginViewModel = viewModel { LoginViewModel() }
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Main) {
                        popUpTo<Login> { inclusive = true }
                    }
                }
            )
        }
        composable<Main> {
            MainScreen(
                onMovieClick = { movie ->
                    navController.navigate(movie.toRoute())
                }
            )
        }
        composable<Detail> { backStackEntry ->
            val route = backStackEntry.toRoute<Detail>()
            DetailScreen(
                movie = route.toMovie(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

package com.oazisn.tmdb

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.oazisn.tmdb.navigation.NavigationHost
import com.oazisn.tmdb.theme.NetflixTheme

@Composable
fun App() {
    NetflixTheme {
        val navController = rememberNavController()
        NavigationHost(navController = navController)
    }
}

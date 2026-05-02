package com.oazisn.tmdb

import androidx.compose.runtime.Composable
import com.oazisn.tmdb.navigation.NavigationHost
import com.oazisn.tmdb.theme.NetflixTheme

@Composable
fun App() {
    NetflixTheme {
        NavigationHost()
    }
}

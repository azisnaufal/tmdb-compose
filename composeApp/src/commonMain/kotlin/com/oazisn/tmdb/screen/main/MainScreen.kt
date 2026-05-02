package com.oazisn.tmdb.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.oazisn.tmdb.theme.NetflixColors

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NetflixColors.BlackCanvas),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Main Screen - Coming Soon",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = NetflixColors.WhitePrimary
            )
        )
    }
}

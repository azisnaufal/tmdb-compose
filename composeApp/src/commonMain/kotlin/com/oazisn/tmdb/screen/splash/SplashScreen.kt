package com.oazisn.tmdb.screen.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.oazisn.tmdb.theme.NetflixColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit
) {
    var startAnimation by remember { mutableFloatStateOf(0f) }
    val alphaAnimation by animateFloatAsState(
        targetValue = startAnimation,
        animationSpec = tween(
            durationMillis = 800,
            easing = LinearEasing
        )
    )

    LaunchedEffect(Unit) {
        startAnimation = 1f
        delay(2000)
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NetflixColors.BlackCanvas),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TMDb",
            style = MaterialTheme.typography.displayLarge.copy(
                color = NetflixColors.RedPrimary
            ),
            modifier = Modifier.alpha(alphaAnimation)
        )
    }
}

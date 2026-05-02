package com.oazisn.tmdb.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NetflixColorScheme = darkColorScheme(
    primary = NetflixColors.RedPrimary,
    onPrimary = NetflixColors.WhitePrimary,
    secondary = NetflixColors.GrayMedium,
    onSecondary = NetflixColors.WhitePrimary,
    background = NetflixColors.BlackCanvas,
    onBackground = NetflixColors.WhitePrimary,
    surface = NetflixColors.GrayDark,
    onSurface = NetflixColors.WhitePrimary,
    surfaceVariant = NetflixColors.GraySurface,
    onSurfaceVariant = NetflixColors.GrayDim,
    outline = NetflixColors.GrayMedium,
    error = NetflixColors.RedPrimary,
    onError = NetflixColors.WhitePrimary
)

@Composable
fun NetflixTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NetflixColorScheme,
        typography = NetflixTypography,
        content = content
    )
}

package com.oazisn.tmdb.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.oazisn.tmdb.component.MainComponent
import com.oazisn.tmdb.component.MainViewState
import com.oazisn.tmdb.screen.components.ErrorState
import com.oazisn.tmdb.screen.components.GenreSection
import com.oazisn.tmdb.screen.components.LoadingIndicator
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing

@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.subscribeAsState()
    val density = LocalDensity.current
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(density).toDp() }
    val navigationBarHeight = with(density) { WindowInsets.navigationBars.getTop(density).toDp() }

    Box(modifier = Modifier.fillMaxSize().background(NetflixColors.BlackCanvas)) {
        when (val s = state) {
            is MainViewState.Loading -> {
                LoadingIndicator(modifier = Modifier.fillMaxSize())
            }
            is MainViewState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Spacer(modifier = Modifier.height(statusBarHeight))
                    }
                    items(s.data, key = { it.genre.id }) { genreWithMovies ->
                        GenreSection(
                            genreWithMovies = genreWithMovies,
                            onMovieClick = { component.onMovieClick(it) }
                        )
                        Spacer(modifier = Modifier.height(NetflixSpacing.XL))
                    }
                    item {
                        Spacer(modifier = Modifier.height(navigationBarHeight))
                    }
                }
            }
            is MainViewState.Error -> {
                ErrorState(
                    message = s.message,
                    onRetry = { component.retry() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

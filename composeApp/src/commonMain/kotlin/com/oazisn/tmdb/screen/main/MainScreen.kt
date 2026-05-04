package com.oazisn.tmdb.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oazisn.tmdb.data.model.Movie
import com.oazisn.tmdb.screen.components.ErrorState
import com.oazisn.tmdb.screen.components.GenreSection
import com.oazisn.tmdb.screen.components.LoadingIndicator
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing

@Composable
fun MainScreen(
    onMovieClick: (Movie) -> Unit,
    viewModel: MainViewModel = viewModel { MainViewModel() }
) {
    when (val state = viewModel.viewState) {
        is MainViewState.Loading -> {
            LoadingIndicator(
                modifier = Modifier.fillMaxSize().background(NetflixColors.BlackCanvas)
            )
        }
        is MainViewState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(NetflixColors.BlackCanvas)
            ) {
                items(state.data, key = { it.genre.id }) { genreWithMovies ->
                    GenreSection(
                        genreWithMovies = genreWithMovies,
                        onMovieClick = onMovieClick
                    )
                    Spacer(modifier = Modifier.height(NetflixSpacing.XL))
                }
            }
        }
        is MainViewState.Error -> {
            ErrorState(
                message = state.message,
                onRetry = { viewModel.loadData() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

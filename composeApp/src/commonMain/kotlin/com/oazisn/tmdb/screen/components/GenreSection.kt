package com.oazisn.tmdb.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oazisn.tmdb.data.model.GenreWithMovies
import com.oazisn.tmdb.data.model.Movie
import com.oazisn.tmdb.theme.NetflixColors
import com.oazisn.tmdb.theme.NetflixSpacing

@Composable
fun GenreSection(
    genreWithMovies: GenreWithMovies,
    cardWidth: Int = 100,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = genreWithMovies.genre.name,
            style = MaterialTheme.typography.headlineSmall,
            color = NetflixColors.RedPrimary,
            modifier = Modifier.padding(
                start = NetflixSpacing.Base,
                end = NetflixSpacing.Base,
                bottom = NetflixSpacing.SM
            )
        )
        MovieCarousel(
            movies = genreWithMovies.movies,
            cardWidth = cardWidth,
            onMovieClick = onMovieClick
        )
    }
}

package com.oazisn.tmdb.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oazisn.tmdb.data.model.Movie
import com.oazisn.tmdb.theme.NetflixSpacing

@Composable
fun MovieCarousel(
    movies: List<Movie>,
    cardWidth: Int = 100,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(NetflixSpacing.XS),
        contentPadding = PaddingValues(
            start = NetflixSpacing.Base,
            end = NetflixSpacing.Base
        )
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieCard(
                movie = movie,
                cardWidth = cardWidth,
                onClick = onMovieClick
            )
        }
    }
}

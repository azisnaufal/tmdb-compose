package com.oazisn.tmdb.data.model

data class GenreWithMovies(
    val genre: Genre,
    val movies: List<Movie>
)

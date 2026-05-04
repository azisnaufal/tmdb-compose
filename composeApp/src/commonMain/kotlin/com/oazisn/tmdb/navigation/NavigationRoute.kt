package com.oazisn.tmdb.navigation

import com.oazisn.tmdb.data.model.Movie
import kotlinx.serialization.Serializable

sealed class NavigationRoute {
    @Serializable
    data object Splash : NavigationRoute()

    @Serializable
    data object Login : NavigationRoute()

    @Serializable
    data object Main : NavigationRoute()

    @Serializable
    data class Detail(val movieId: Int, val movieTitle: String, val movieOverview: String, val movieBackdropPath: String?, val moviePosterPath: String?, val movieVoteAverage: Double, val movieReleaseDate: String) : NavigationRoute()
}

fun Movie.toRoute() = NavigationRoute.Detail(
    movieId = id,
    movieTitle = title,
    movieOverview = overview,
    movieBackdropPath = backdropPath,
    moviePosterPath = posterPath,
    movieVoteAverage = voteAverage,
    movieReleaseDate = releaseDate
)

fun NavigationRoute.Detail.toMovie() = Movie(
    id = movieId,
    title = movieTitle,
    overview = movieOverview,
    backdropPath = movieBackdropPath,
    posterPath = moviePosterPath,
    voteAverage = movieVoteAverage,
    releaseDate = movieReleaseDate
)

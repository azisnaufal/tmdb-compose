package com.oazisn.tmdb.navigation

import com.oazisn.tmdb.data.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data object Splash

@Serializable
data object Login

@Serializable
data object Main

@Serializable
data class Detail(
    val movieId: Int,
    val movieTitle: String,
    val movieOverview: String,
    val movieBackdropPath: String?,
    val moviePosterPath: String?,
    val movieVoteAverage: Double,
    val movieReleaseDate: String
)

fun Movie.toRoute() = Detail(
    movieId = id,
    movieTitle = title,
    movieOverview = overview,
    movieBackdropPath = backdropPath,
    moviePosterPath = posterPath,
    movieVoteAverage = voteAverage,
    movieReleaseDate = releaseDate
)

fun Detail.toMovie() = Movie(
    id = movieId,
    title = movieTitle,
    overview = movieOverview,
    backdropPath = movieBackdropPath,
    posterPath = moviePosterPath,
    voteAverage = movieVoteAverage,
    releaseDate = movieReleaseDate
)

package com.oazisn.tmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String = "",
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("release_date") val releaseDate: String = "",
    @SerialName("genre_ids") val genreIds: List<Int> = emptyList(),
    val adult: Boolean = false,
    val popularity: Double = 0.0
)

@Serializable
data class MovieResponse(
    val page: Int = 1,
    val results: List<Movie> = emptyList(),
    @SerialName("total_pages") val totalPages: Int = 0,
    @SerialName("total_results") val totalResults: Int = 0
)

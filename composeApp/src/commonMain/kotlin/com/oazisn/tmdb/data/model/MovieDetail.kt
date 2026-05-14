package com.oazisn.tmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String = "",
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("release_date") val releaseDate: String = "",
    val runtime: Int = 0,
    val tagline: String = "",
    val genres: List<Genre> = emptyList(),
    val adult: Boolean = false,
    val popularity: Double = 0.0,
    val status: String = ""
)

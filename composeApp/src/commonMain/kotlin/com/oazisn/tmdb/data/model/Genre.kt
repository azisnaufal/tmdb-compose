package com.oazisn.tmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String
)

@Serializable
data class GenreResponse(
    val genres: List<Genre> = emptyList()
)

package com.oazisn.tmdb.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.oazisn.tmdb.data.model.MovieDetail
import com.oazisn.tmdb.database.TmdbDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(private val database: TmdbDatabase) {

    fun getAll(): Flow<List<Favorite>> =
        database.favoriteQueries.selectAll().asFlow().mapToList(Dispatchers.IO).map { rows ->
            rows.map { Favorite(it.id, it.genre, it.title, it.poster_path) }
        }

    fun isFavorite(movieId: Int): Flow<Boolean> =
        database.favoriteQueries.selectById(movieId.toLong()).asFlow()
            .mapToOneOrNull(Dispatchers.IO).map { it != null }

    fun addFavorite(movie: MovieDetail, genre: String) {
        database.favoriteQueries.insert(
            id = movie.id.toLong(),
            genre = genre,
            title = movie.title,
            poster_path = movie.posterPath ?: ""
        )
    }

    fun removeFavorite(movieId: Int) {
        database.favoriteQueries.delete(movieId.toLong())
    }

    fun toggleFavorite(movie: MovieDetail, genre: String, isFavorite: Boolean) {
        if (isFavorite) {
            removeFavorite(movie.id)
        } else {
            addFavorite(movie, genre)
        }
    }
}

data class Favorite(
    val id: Long,
    val genre: String,
    val title: String,
    val posterPath: String
)

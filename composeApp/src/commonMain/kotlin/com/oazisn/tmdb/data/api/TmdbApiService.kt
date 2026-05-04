package com.oazisn.tmdb.data.api

import com.oazisn.tmdb.data.model.GenreResponse
import com.oazisn.tmdb.data.model.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TmdbApiService(private val client: HttpClient = TmdbApiClient.create()) {

    suspend fun getGenres(): GenreResponse = client.get("/genre/movie/list") {
        parameter("language", "en")
    }.body()

    suspend fun getMoviesByGenre(genreId: Int, page: Int = 1): MovieResponse =
        client.get("/discover/movie") {
            parameter("include_adult", "false")
            parameter("include_video", "false")
            parameter("language", "en-US")
            parameter("page", page)
            parameter("sort_by", "popularity.desc")
            parameter("with_genres", genreId.toString())
        }.body()
}

package com.oazisn.tmdb.data.repository

import com.oazisn.tmdb.data.api.TmdbApiService
import com.oazisn.tmdb.data.model.GenreWithMovies
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException

class MovieRepository(
    private val apiService: TmdbApiService = TmdbApiService()
) {
    suspend fun getGenresWithMovies(): Result<List<GenreWithMovies>> = try {
        val genreResponse = apiService.getGenres()
        val genresWithMovies = genreResponse.genres.map { genre ->
            val moviesResponse = apiService.getMoviesByGenre(genre.id)
            GenreWithMovies(
                genre = genre,
                movies = moviesResponse.results
            )
        }
        Result.success(genresWithMovies)
    } catch (e: ClientRequestException) {
        Result.failure(UserFriendlyException("Unable to load content. Please try again."))
    } catch (e: ServerResponseException) {
        Result.failure(UserFriendlyException("Something went wrong on our end. Please try again later."))
    } catch (e: UnresolvedAddressException) {
        Result.failure(UserFriendlyException("Unable to connect. Please check your internet connection."))
    } catch (e: Exception) {
        Result.failure(UserFriendlyException("An unexpected error occurred. Please try again."))
    }
}

class UserFriendlyException(val userMessage: String) : Exception(userMessage)

package com.oazisn.tmdb.data.api

import com.oazisn.tmdb.getTmdbApiKey
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object TmdbApiClient {
    fun create(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                encodedPath = "/3$encodedPath"
            }
            header("Authorization", "Bearer ${getTmdbApiKey()}")
            header("accept", "application/json")
            contentType(ContentType.Application.Json)
        }
    }
}

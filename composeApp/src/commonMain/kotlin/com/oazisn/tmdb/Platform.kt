package com.oazisn.tmdb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getTmdbApiKey(): String
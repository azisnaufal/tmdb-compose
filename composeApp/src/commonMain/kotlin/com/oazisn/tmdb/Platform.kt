package com.oazisn.tmdb

import app.cash.sqldelight.db.SqlDriver

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getTmdbApiKey(): String

expect fun createDatabaseDriver(): SqlDriver
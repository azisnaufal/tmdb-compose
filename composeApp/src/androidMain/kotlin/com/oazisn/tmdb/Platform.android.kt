package com.oazisn.tmdb

import android.os.Build
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.oazisn.tmdb.database.TmdbDatabase

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getTmdbApiKey(): String = BuildConfig.TMDB_API_KEY

lateinit var appContext: android.content.Context

actual fun createDatabaseDriver(): SqlDriver = AndroidSqliteDriver(TmdbDatabase.Schema, appContext, "tmdb.db")
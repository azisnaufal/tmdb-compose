package com.oazisn.tmdb

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.oazisn.tmdb.database.TmdbDatabase
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getTmdbApiKey(): String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOGM2ZWEzZGI2ODBmYWU1MDI0MTZkODBmZjQxNmEwYiIsIm5iZiI6MTUzNTYxMDY4Ni4wMjIsInN1YiI6IjViODc4ZjNlOTI1MTQxMWZkZTAwMjQxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MiVcLYgIQdOgmMWr_iJ_7QEevrOdz47MLK85GAQvGEo"

actual fun createDatabaseDriver(): SqlDriver = NativeSqliteDriver(TmdbDatabase.Schema, "tmdb.db")
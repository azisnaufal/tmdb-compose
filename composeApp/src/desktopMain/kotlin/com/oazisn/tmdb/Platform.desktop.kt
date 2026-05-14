package com.oazisn.tmdb

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.oazisn.tmdb.database.TmdbDatabase

class DesktopPlatform : Platform {
    override val name: String = "Desktop (${System.getProperty("os.name")})"
}

actual fun getPlatform(): Platform = DesktopPlatform()

actual fun getTmdbApiKey(): String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOGM2ZWEzZGI2ODBmYWU1MDI0MTZkODBmZjQxNmEwYiIsIm5iZiI6MTUzNTYxMDY4Ni4wMjIsInN1YiI6IjViODc4ZjNlOTI1MTQxMWZkZTAwMjQxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MiVcLYgIQdOgmMWr_iJ_7QEevrOdz47MLK85GAQvGEo"

actual fun createDatabaseDriver(): SqlDriver {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    TmdbDatabase.Schema.create(driver)
    return driver
}

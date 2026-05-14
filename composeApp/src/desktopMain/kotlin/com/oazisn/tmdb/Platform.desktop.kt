package com.oazisn.tmdb

class DesktopPlatform : Platform {
    override val name: String = "Desktop (${System.getProperty("os.name")})"
}

actual fun getPlatform(): Platform = DesktopPlatform()

actual fun getTmdbApiKey(): String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOGM2ZWEzZGI2ODBmYWU1MDI0MTZkODBmZjQxNmEwYiIsIm5iZiI6MTUzNTYxMDY4Ni4wMjIsInN1YiI6IjViODc4ZjNlOTI1MTQxMWZkZTAwMjQxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MiVcLYgIQdOgmMWr_iJ_7QEevrOdz47MLK85GAQvGEo"

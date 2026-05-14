package com.oazisn.tmdb

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TMDB",
    ) {
        App(DefaultComponentContext(lifecycle = LifecycleRegistry()))
    }
}

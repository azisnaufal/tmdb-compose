package com.oazisn.tmdb

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun MainViewController() = ComposeUIViewController {
    App(DefaultComponentContext(lifecycle = LifecycleRegistry()))
}

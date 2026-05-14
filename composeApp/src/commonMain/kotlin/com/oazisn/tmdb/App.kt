package com.oazisn.tmdb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.oazisn.tmdb.component.DefaultRootComponent
import com.oazisn.tmdb.component.RootComponent
import com.oazisn.tmdb.database.TmdbDatabase
import com.oazisn.tmdb.screen.detail.DetailScreen
import com.oazisn.tmdb.screen.login.LoginScreen
import com.oazisn.tmdb.screen.main.MainScreen
import com.oazisn.tmdb.screen.splash.SplashScreen
import com.oazisn.tmdb.theme.NetflixTheme

@Composable
fun App(componentContext: ComponentContext) {
    NetflixTheme {
        val database = remember { TmdbDatabase(createDatabaseDriver()) }
        val rootComponent = remember(componentContext) {
            DefaultRootComponent(componentContext = componentContext, database = database)
        }

        Children(
            stack = rootComponent.stack,
            animation = stackAnimation(fade())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Splash -> SplashScreen(instance.component)
                is RootComponent.Child.Login -> LoginScreen(instance.component)
                is RootComponent.Child.Main -> MainScreen(instance.component)
                is RootComponent.Child.Detail -> DetailScreen(instance.component)
            }
        }
    }
}

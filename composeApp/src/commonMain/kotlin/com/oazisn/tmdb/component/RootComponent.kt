package com.oazisn.tmdb.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.oazisn.tmdb.data.model.Movie
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Splash(val component: SplashComponent) : Child()
        class Login(val component: LoginComponent) : Child()
        class Main(val component: MainComponent) : Child()
        class Detail(val component: DetailComponent) : Child()
    }
}

@OptIn(DelicateDecomposeApi::class)
class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _stack: Value<ChildStack<Config, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Splash,
        serializer = Config.serializer(),
        childFactory = ::createChild
    )

    override val stack: Value<ChildStack<*, RootComponent.Child>> = _stack

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Splash -> RootComponent.Child.Splash(
                DefaultSplashComponent(
                    componentContext = componentContext,
                    onNavigateToLogin = { navigation.replaceAll(Config.Login) }
                )
            )
            is Config.Login -> RootComponent.Child.Login(
                DefaultLoginComponent(
                    componentContext = componentContext,
                    onLoginSuccess = { navigation.replaceAll(Config.Main) }
                )
            )
            is Config.Main -> RootComponent.Child.Main(
                DefaultMainComponent(
                    componentContext = componentContext,
                    onMovieClick = { movie: Movie -> navigation.push(Config.Detail(movie)) }
                )
            )
            is Config.Detail -> RootComponent.Child.Detail(
                DefaultDetailComponent(
                    componentContext = componentContext,
                    movie = config.movie,
                    onBack = { navigation.pop() }
                )
            )
        }

    @Serializable
    sealed class Config {
        @Serializable data object Splash : Config()
        @Serializable data object Login : Config()
        @Serializable data object Main : Config()
        @Serializable data class Detail(val movie: Movie) : Config()
    }
}

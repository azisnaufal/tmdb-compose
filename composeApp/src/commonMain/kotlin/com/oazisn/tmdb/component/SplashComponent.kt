package com.oazisn.tmdb.component

import com.arkivanov.decompose.ComponentContext

interface SplashComponent {
    fun onNavigateToLogin()
}

class DefaultSplashComponent(
    componentContext: ComponentContext,
    onNavigateToLogin: () -> Unit
) : SplashComponent, ComponentContext by componentContext {

    private val _onNavigateToLogin = onNavigateToLogin

    override fun onNavigateToLogin() = _onNavigateToLogin()
}

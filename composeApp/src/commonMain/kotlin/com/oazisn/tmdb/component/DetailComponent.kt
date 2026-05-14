package com.oazisn.tmdb.component

import com.arkivanov.decompose.ComponentContext
import com.oazisn.tmdb.data.model.Movie

interface DetailComponent {
    val movie: Movie
    fun onBack()
}

class DefaultDetailComponent(
    componentContext: ComponentContext,
    override val movie: Movie,
    onBack: () -> Unit
) : DetailComponent, ComponentContext by componentContext {

    private val _onBack = onBack

    override fun onBack() = _onBack()
}

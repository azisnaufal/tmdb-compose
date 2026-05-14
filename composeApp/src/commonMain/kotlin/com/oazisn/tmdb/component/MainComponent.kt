package com.oazisn.tmdb.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.oazisn.tmdb.data.model.GenreWithMovies
import com.oazisn.tmdb.data.model.Movie
import com.oazisn.tmdb.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

interface MainComponent {
    val state: Value<MainViewState>
    fun onMovieClick(movie: Movie)
    fun retry()
}

sealed class MainViewState {
    data object Loading : MainViewState()
    data class Success(val data: List<GenreWithMovies>) : MainViewState()
    data class Error(val message: String) : MainViewState()
}

class DefaultMainComponent(
    componentContext: ComponentContext,
    onMovieClick: (Movie) -> Unit,
    private val repository: MovieRepository = MovieRepository()
) : MainComponent, ComponentContext by componentContext {

    private val _onMovieClick = onMovieClick

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state: MutableValue<MainViewState> = MutableValue(MainViewState.Loading)
    override val state: Value<MainViewState> = _state

    init {
        lifecycle.doOnDestroy { scope.cancel() }
        loadData()
    }

    override fun onMovieClick(movie: Movie) = _onMovieClick(movie)

    override fun retry() {
        loadData()
    }

    private fun loadData() {
        _state.update { MainViewState.Loading }
        scope.launch {
            val result = repository.getGenresWithMovies()
            _state.update {
                if (result.isSuccess) {
                    val data = result.getOrDefault(emptyList())
                    if (data.isEmpty()) {
                        MainViewState.Error("No content found. Please try again.")
                    } else {
                        MainViewState.Success(data)
                    }
                } else {
                    val message = result.exceptionOrNull()?.message
                        ?: "An unexpected error occurred. Please try again."
                    MainViewState.Error(message)
                }
            }
        }
    }
}

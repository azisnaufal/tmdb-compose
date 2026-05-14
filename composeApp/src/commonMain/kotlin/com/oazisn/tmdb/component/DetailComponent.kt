package com.oazisn.tmdb.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.oazisn.tmdb.data.model.MovieDetail
import com.oazisn.tmdb.data.repository.FavoriteRepository
import com.oazisn.tmdb.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface DetailComponent {
    val state: Value<DetailViewState>
    val isFavorite: StateFlow<Boolean>
    fun onBack()
    fun toggleFavorite()
}

sealed class DetailViewState {
    data object Loading : DetailViewState()
    data class Success(val movie: MovieDetail) : DetailViewState()
    data class Error(val message: String) : DetailViewState()
}

class DefaultDetailComponent(
    componentContext: ComponentContext,
    private val movieId: Int,
    private val movieRepository: MovieRepository = MovieRepository(),
    private val favoriteRepository: FavoriteRepository,
    onBack: () -> Unit
) : DetailComponent, ComponentContext by componentContext {

    private val _onBack = onBack

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state: MutableValue<DetailViewState> = MutableValue(DetailViewState.Loading)
    override val state: Value<DetailViewState> = _state

    override val isFavorite: StateFlow<Boolean> = favoriteRepository.isFavorite(movieId)
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), false)

    init {
        lifecycle.doOnDestroy { scope.cancel() }
        loadMovieDetail()
    }

    override fun onBack() = _onBack()

    override fun toggleFavorite() {
        val current = _state.value
        if (current !is DetailViewState.Success) return
        val genre = current.movie.genres.joinToString(", ") { it.name }
        favoriteRepository.toggleFavorite(current.movie, genre, isFavorite.value)
    }

    private fun loadMovieDetail() {
        _state.update { DetailViewState.Loading }
        scope.launch {
            val result = movieRepository.getMovieDetail(movieId)
            _state.update {
                if (result.isSuccess) {
                    val detail = result.getOrDefault(MovieDetail(id = movieId, title = ""))
                    if (detail.title.isBlank() && detail.overview.isBlank()) {
                        DetailViewState.Error("Unable to load movie details. Please try again.")
                    } else {
                        DetailViewState.Success(detail)
                    }
                } else {
                    val message = result.exceptionOrNull()?.message
                        ?: "An unexpected error occurred. Please try again."
                    DetailViewState.Error(message)
                }
            }
        }
    }
}

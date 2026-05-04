package com.oazisn.tmdb.screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oazisn.tmdb.data.model.GenreWithMovies
import com.oazisn.tmdb.data.repository.MovieRepository
import kotlinx.coroutines.launch

sealed class MainViewState {
    data object Loading : MainViewState()
    data class Success(val data: List<GenreWithMovies>) : MainViewState()
    data class Error(val message: String) : MainViewState()
}

class MainViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {
    var viewState by mutableStateOf<MainViewState>(MainViewState.Loading)
        private set

    init {
        loadData()
    }

    fun loadData() {
        viewState = MainViewState.Loading
        viewModelScope.launch {
            val result = repository.getGenresWithMovies()
            viewState = if (result.isSuccess) {
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

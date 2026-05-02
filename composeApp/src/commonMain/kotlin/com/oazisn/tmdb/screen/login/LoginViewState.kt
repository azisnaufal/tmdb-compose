package com.oazisn.tmdb.screen.login

sealed class LoginViewState {
    data object Idle : LoginViewState()
    data object Loading : LoginViewState()
    data class Error(val message: String) : LoginViewState()
    data object Success : LoginViewState()
}

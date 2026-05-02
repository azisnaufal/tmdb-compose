package com.oazisn.tmdb.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var viewState by mutableStateOf<LoginViewState>(LoginViewState.Idle)
        private set

    fun onUsernameChanged(value: String) {
        username = value
        viewState = LoginViewState.Idle
    }

    fun onPasswordChanged(value: String) {
        password = value
        viewState = LoginViewState.Idle
    }

    fun login(onSuccess: () -> Unit) {
        if (username.isBlank() || password.isBlank()) {
            viewState = LoginViewState.Error("Please enter username and password")
            return
        }

        viewState = LoginViewState.Loading

        if (username == "admin" && password == "admin") {
            viewState = LoginViewState.Success
            onSuccess()
        } else {
            viewState = LoginViewState.Error("Wrong credentials. Please try again.")
        }
    }
}

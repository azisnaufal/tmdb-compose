package com.oazisn.tmdb.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.oazisn.tmdb.screen.login.LoginViewState

interface LoginComponent {
    val state: Value<LoginState>
    fun onUsernameChanged(value: String)
    fun onPasswordChanged(value: String)
    fun login()
}

data class LoginState(
    val username: String = "",
    val password: String = "",
    val viewState: LoginViewState = LoginViewState.Idle
)

class DefaultLoginComponent(
    componentContext: ComponentContext,
    onLoginSuccess: () -> Unit
) : LoginComponent, ComponentContext by componentContext {

    private val _onLoginSuccess = onLoginSuccess

    private val _state = MutableValue(LoginState())
    override val state: Value<LoginState> = _state

    override fun onUsernameChanged(value: String) {
        _state.update { it.copy(username = value, viewState = LoginViewState.Idle) }
    }

    override fun onPasswordChanged(value: String) {
        _state.update { it.copy(password = value, viewState = LoginViewState.Idle) }
    }

    override fun login() {
        val current = _state.value
        if (current.username.isBlank() || current.password.isBlank()) {
            _state.update { it.copy(viewState = LoginViewState.Error("Please enter username and password")) }
            return
        }

        _state.update { it.copy(viewState = LoginViewState.Loading) }

        if (current.username == "admin" && current.password == "admin") {
            _state.update { it.copy(viewState = LoginViewState.Success) }
            _onLoginSuccess()
        } else {
            _state.update { it.copy(viewState = LoginViewState.Error("Wrong credentials. Please try again.")) }
        }
    }
}

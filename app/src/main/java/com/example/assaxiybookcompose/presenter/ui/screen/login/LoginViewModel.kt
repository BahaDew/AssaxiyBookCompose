package com.example.assaxiybookcompose.presenter.ui.screen.login

import kotlinx.coroutines.flow.StateFlow

interface LoginViewModel {
    val loginState: StateFlow<LoginState>

    fun onDispatch(intent: LoginIntent)

}

data class LoginState(
    val message: String = ""
)

sealed interface LoginIntent {
    data object OnClickRegister : LoginIntent
    data class OnClickNext(val email: String, val password: String) : LoginIntent
}
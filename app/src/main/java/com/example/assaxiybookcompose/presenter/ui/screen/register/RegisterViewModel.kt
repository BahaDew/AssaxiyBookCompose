package com.example.assaxiybookcompose.presenter.ui.screen.register

import kotlinx.coroutines.flow.StateFlow

interface RegisterViewModel {

    val registerState: StateFlow<RegisterState>

    fun onDispatch(intent: RegisterIntent)
}

sealed interface RegisterIntent {
    data object OnClickBack : RegisterIntent
    data class OnClickSubmit(
        val fullName: String,
        val email: String,
        val password: String
    ) : RegisterIntent
}

data class RegisterState(
    val message : String = ""
)
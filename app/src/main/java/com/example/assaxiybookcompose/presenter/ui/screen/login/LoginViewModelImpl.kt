package com.example.assaxiybookcompose.presenter.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.assaxiybookcompose.presenter.ui.screen.main.MainScreen
import com.example.assaxiybookcompose.presenter.ui.screen.register.RegisterScreen
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.example.assaxiybookcompose.presenter.ui.screen.login.LoginIntent.*
import com.example.assaxiybookcompose.utils.bahaLogger
import kotlinx.coroutines.flow.launchIn


@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), LoginViewModel {
    override val loginState = MutableStateFlow(LoginState())

    override fun onDispatch(intent: LoginIntent) {
        bahaLogger(" you win $intent")
        when (intent) {
            is OnClickRegister -> {
                viewModelScope.launch {
                    appNavigator.push(RegisterScreen())
                }
            }

            is OnClickNext -> {
                appRepository.loginUser(
                    password = intent.password,
                    gmail = intent.email
                ).onEach {
                    it.onSuccess {
                        appNavigator.replaceAll(MainScreen())
                    }.onFailure { thr ->
                        loginState.value = LoginState(message = thr.message ?: "")
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

}
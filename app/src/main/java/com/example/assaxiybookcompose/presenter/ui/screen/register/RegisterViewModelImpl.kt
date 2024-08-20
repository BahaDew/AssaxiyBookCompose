package com.example.assaxiybookcompose.presenter.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.main.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.assaxiybookcompose.presenter.ui.screen.register.RegisterIntent.*
import com.example.assaxiybookcompose.utils.bahaLogger
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), RegisterViewModel {
    override val registerState = MutableStateFlow(RegisterState())

    override fun onDispatch(intent: RegisterIntent) {
        when (intent) {
            is OnClickSubmit -> {
                appRepository.registerUser(
                    name = intent.fullName,
                    gmail = intent.email,
                    password = intent.password
                ).onEach {
                    it.onSuccess {
                        appNavigator.replaceAll(MainScreen())
                    }.onFailure {thr ->
                        registerState.value = RegisterState(thr.message ?: "")
                    }
                }.launchIn(viewModelScope)
            }

            is OnClickBack -> {
                viewModelScope.launch {
                    appNavigator.pop()
                }
            }
        }
    }
}
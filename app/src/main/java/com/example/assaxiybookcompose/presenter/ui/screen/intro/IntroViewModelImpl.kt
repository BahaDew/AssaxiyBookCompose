package com.example.assaxiybookcompose.presenter.ui.screen.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.login.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), IntroViewModel {
    override fun onClickStart() {
        viewModelScope.launch {
            appNavigator.replace(LoginScreen())
        }
    }
}
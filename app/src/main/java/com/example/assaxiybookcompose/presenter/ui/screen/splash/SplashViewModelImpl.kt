package com.example.assaxiybookcompose.presenter.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.intro.IntroScreen
import com.example.assaxiybookcompose.presenter.ui.screen.login.LoginScreen
import com.example.assaxiybookcompose.presenter.ui.screen.main.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), SplashViewModel {

    override fun openSplash() {
        viewModelScope.launch {
            delay(2000)
            if (appRepository.getIntroState()) {
                if (appRepository.getLoginState()) {
                    appNavigator.replace(MainScreen())
                } else {
                    appNavigator.replace(LoginScreen())
                }
            } else {
                appNavigator.replace(IntroScreen())
            }
        }
    }
}
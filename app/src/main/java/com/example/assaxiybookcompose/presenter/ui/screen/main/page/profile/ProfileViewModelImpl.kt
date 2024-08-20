package com.example.assaxiybookcompose.presenter.ui.screen.main.page.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.card_bought.CardBoughtScreen
import com.example.assaxiybookcompose.presenter.ui.screen.login.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), ProfileViewModel {
    override val uiState = MutableStateFlow(ProfileState())

    init {
        uiState.update { state -> state.copy(userData = appRepository.getUserData()) }
    }

    override fun onEvent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.OnCLickCard -> {
                viewModelScope.launch {
                    appNavigator.push(CardBoughtScreen())
                }
            }

            is ProfileIntent.OnCLickLogOut -> {
                viewModelScope.launch {
                    appRepository.logOut()
                    appNavigator.replaceAll(LoginScreen())
                }
            }
        }
    }
}
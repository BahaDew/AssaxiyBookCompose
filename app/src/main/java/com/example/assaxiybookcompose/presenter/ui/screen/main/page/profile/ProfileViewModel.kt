package com.example.assaxiybookcompose.presenter.ui.screen.main.page.profile

import com.example.assaxiybookcompose.data.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface ProfileViewModel {
    val uiState: StateFlow<ProfileState>

    fun onEvent(intent: ProfileIntent)
}

sealed interface ProfileIntent {
    data object OnCLickCard : ProfileIntent
    data object OnCLickLogOut : ProfileIntent
}

data class ProfileState(
    val userData: UserData = UserData()
)
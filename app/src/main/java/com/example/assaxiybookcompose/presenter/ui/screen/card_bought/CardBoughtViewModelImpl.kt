package com.example.assaxiybookcompose.presenter.ui.screen.card_bought

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.info.InfoScreen
import com.example.assaxiybookcompose.utils.bahaLogger
import com.example.assaxiybookcompose.utils.toInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardBoughtViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), CardBoughtViewModel {
    override val uiState = MutableStateFlow(CardBoughtState())

    init {
        appRepository.getUserBoughtBooks()
            .onEach { result ->
                result.onSuccess { book ->
                    uiState.update {
                        it.copy(booksList = book)
                    }
                }.onFailure {
                    bahaLogger(it.message ?: "Nomalum xatolik")
                }
            }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: CardBoughtIntent) {
        when (intent) {
            is CardBoughtIntent.OnClickBack -> {
                viewModelScope.launch {
                    appNavigator.pop()
                }
            }

            is CardBoughtIntent.OnClickBook -> {
                viewModelScope.launch {
                    appNavigator.push(InfoScreen())
                    toInfo.emit(intent.bookUIData)
                }
            }
        }
    }
}
package com.example.assaxiybookcompose.presenter.ui.screen.search

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
class SearchViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), SearchViewModel {
    override val uiState = MutableStateFlow(SearchState())
    private var time = System.currentTimeMillis()
    override fun onEventDispatcher(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OnClickBack -> {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    viewModelScope.launch {
                        appNavigator.pop()
                    }
                }
            }

            is SearchIntent.TextChange -> {
                if (intent.text.isEmpty()) {
                    uiState.update {
                        it.copy(resultBooksList = emptyList())
                    }
                } else {
                    appRepository.getBooksByName(name = intent.text)
                        .onEach { result ->
                            result.onSuccess { list ->
                                uiState.update {
                                    it.copy(resultBooksList = list)
                                }
                            }.onFailure {
                                bahaLogger(it.message ?: "Nomalum xatolik!")
                            }
                        }.launchIn(viewModelScope)
                }
            }

            is SearchIntent.OnClickBook -> {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    viewModelScope.launch {
                        appNavigator.push(InfoScreen())
                        toInfo.emit(intent.bookUIData)
                    }
                }
            }
        }
    }

}
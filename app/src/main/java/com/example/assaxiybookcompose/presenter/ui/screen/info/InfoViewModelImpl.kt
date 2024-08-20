package com.example.assaxiybookcompose.presenter.ui.screen.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.read_pdf.ReadPdfScreen
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
class InfoViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), InfoViewModel {
    override val uiState = MutableStateFlow(InfoState())

    init {
        toInfo.onEach {
            setBookUiData(it)
        }.launchIn(viewModelScope)
    }

    override fun setBookUiData(bookUIData: BookUIData) {
        appRepository.getBookPath(bookUIData).onEach {
            it.onSuccess {
                uiState.update { infoState ->
                    infoState.copy(
                        bookUIData = bookUIData,
                        isDownload = true,
                        bookPath = it
                    )
                }
            }
            it.onFailure {
                uiState.update { infoState ->
                    infoState.copy(
                        bookUIData = bookUIData,
                        isDownload = false
                    )
                }
            }

        }.launchIn(viewModelScope)

    }

    override fun onEvent(intent: InfoIntent) {
        when (intent) {
            is InfoIntent.OnClickBack -> {
                viewModelScope.launch {
                    appNavigator.pop()
                }
            }

            is InfoIntent.OnClickRead -> {
                if (uiState.value.isDownload) {
                    uiState.update { infoState -> infoState.copy(openDefaultPdfReader = true) }
                } else {
                    uiState.update { infoState -> infoState.copy(isLoading = true) }
                    appRepository
                        .buyBook(uiState.value.bookUIData)
                        .onEach {
                            it.onSuccess { path ->
                                uiState.update { infoState ->
                                    infoState.copy(
                                        isLoading = false,
                                        bookPath = path,
                                        isDownload = true
                                    )
                                }
                                bahaLogger("yuklandi : $path")
                            }
                            it.onFailure { thr ->
                                bahaLogger("yuklanmadi : " + thr.message)
                                uiState.update { infoState -> infoState.copy(isLoading = false) }
                            }
                        }
                        .launchIn(viewModelScope)
                }
            }

            is InfoIntent.NotReadDefaultPdfReader -> {
                viewModelScope.launch {
                    appNavigator.push(ReadPdfScreen(uiState.value.bookPath))
                }
            }

            is InfoIntent.OpenPdfReader -> {
                uiState.update { infoState -> infoState.copy(openDefaultPdfReader = false) }
            }
        }
    }
}
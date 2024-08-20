package com.example.assaxiybookcompose.presenter.ui.screen.read_pdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadPdfViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), ReadPdfViewModel {
    override val uiState = MutableStateFlow(ReadPdfState())

    override fun setBookUrl(bookUrl: String) {
        uiState.update { state -> state.copy(bookUrl = bookUrl) }
    }

    override fun onEvent(intent: ReadPdfIntent) {
        when (intent) {
            is ReadPdfIntent.OnClickBack -> {
                viewModelScope.launch {
                    appNavigator.pop()
                }
            }
        }
    }

}
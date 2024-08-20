package com.example.assaxiybookcompose.presenter.ui.screen.category_by_books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.info.InfoScreen
import com.example.assaxiybookcompose.utils.toCategoryPdf
import com.example.assaxiybookcompose.utils.toInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryByBooksViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), CategoryByBooksViewModel {
    override val uiState = MutableStateFlow(CategoryBookState())
    private var time = System.currentTimeMillis()

    init {
        toCategoryPdf
            .onEach {
                uiState.update { categoryBookState -> categoryBookState.copy(categoryBooks = it) }
            }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: CategoryBooksIntent) {
        when (intent) {
            is CategoryBooksIntent.OnClickBack -> {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    viewModelScope.launch {
                        appNavigator.pop()
                    }
                }
            }

            is CategoryBooksIntent.OnClickBook -> {
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
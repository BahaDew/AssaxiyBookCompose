package com.example.assaxiybookcompose.presenter.ui.screen.main.page.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.category_by_books.CategoryByBooksScreen
import com.example.assaxiybookcompose.presenter.ui.screen.info.InfoScreen
import com.example.assaxiybookcompose.presenter.ui.screen.search.SearchScreen
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
class LibraryViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), LibraryViewModel {
    override val libraryState = MutableStateFlow(LibraryState())
    private var time = System.currentTimeMillis()

    init {
        libraryState.update { state -> state.copy(progressState = true) }
        appRepository.getCategoryByPdfBooks()
            .onEach {
                it.onSuccess { list ->
                    libraryState.update { state ->
                        state.copy(
                            progressState = false,
                            libraryListData = list
                        )
                    }
                }.onFailure {
                    libraryState.update { state -> state.copy(progressState = false) }
                }
            }.launchIn(viewModelScope)
    }

    override fun libraryEvent(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.OnClickBook -> {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    viewModelScope.launch {
                        appNavigator.push(InfoScreen())
                        toInfo.emit(intent.bookUIData)
                    }
                }
            }

            is LibraryIntent.OnClickCategory -> {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    viewModelScope.launch {
                        appNavigator.push(CategoryByBooksScreen())
                        toCategoryPdf.emit(intent.categoryByBooksUIData)
                    }
                }
            }

            is LibraryIntent.OnClickSearch -> {
                viewModelScope.launch {
                    appNavigator.push(SearchScreen())
                }
            }
        }
    }
}
package com.example.assaxiybookcompose.presenter.ui.screen.main.page.books

import androidx.lifecycle.ViewModel
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BooksViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), BooksViewModel {
    override val uiState = MutableStateFlow(BookState())

    override fun onEvent(intent: BookIntent) {
        when (intent) {
            is BookIntent.OnClickReadBook -> {
                // bu yerda qanday ko'd yozishim kerak
            }
        }
    }
}
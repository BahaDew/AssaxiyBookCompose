package com.example.assaxiybookcompose.presenter.ui.screen.main.page.books

import kotlinx.coroutines.flow.StateFlow

interface BooksViewModel {
    val uiState: StateFlow<BookState>
    fun onEvent(intent: BookIntent)
}

sealed interface BookIntent {
    data object OnClickReadBook : BookIntent
}

data class BookState(
    val toLibrary: Boolean = false
)
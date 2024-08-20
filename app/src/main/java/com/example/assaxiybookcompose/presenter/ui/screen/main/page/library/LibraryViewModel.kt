package com.example.assaxiybookcompose.presenter.ui.screen.main.page.library

import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.CategoryByBooksUIData
import kotlinx.coroutines.flow.StateFlow

interface LibraryViewModel {

    val libraryState: StateFlow<LibraryState>

    fun libraryEvent(intent: LibraryIntent)
}

sealed interface LibraryIntent {
    data class OnClickBook(val bookUIData: BookUIData) : LibraryIntent
    data object OnClickSearch : LibraryIntent
    data class OnClickCategory(val categoryByBooksUIData: CategoryByBooksUIData) : LibraryIntent
}

data class LibraryState(
    val libraryListData: List<CategoryByBooksUIData> = arrayListOf(),
    val progressState : Boolean = false
)
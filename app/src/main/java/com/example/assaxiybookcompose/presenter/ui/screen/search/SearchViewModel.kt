package com.example.assaxiybookcompose.presenter.ui.screen.search

import android.os.Parcelable
import com.example.assaxiybookcompose.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface SearchViewModel {
    val uiState : StateFlow<SearchState>

    fun onEventDispatcher(intent: SearchIntent)
}

sealed interface SearchIntent {
    data object OnClickBack : SearchIntent

    @Parcelize
    data class TextChange(
        val text: String
    ) : SearchIntent, Parcelable

    @Parcelize
    data class OnClickBook(
        val bookUIData: BookUIData
    ) : SearchIntent, Parcelable
}

data class SearchState(
    val resultBooksList: List<BookUIData> = emptyList()
)
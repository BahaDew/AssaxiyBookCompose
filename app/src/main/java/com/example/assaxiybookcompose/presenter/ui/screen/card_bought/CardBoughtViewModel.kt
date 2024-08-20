package com.example.assaxiybookcompose.presenter.ui.screen.card_bought

import android.os.Parcelable
import com.example.assaxiybookcompose.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface CardBoughtViewModel {

    val uiState: StateFlow<CardBoughtState>

    fun onEventDispatcher(intent: CardBoughtIntent)
}

sealed interface CardBoughtIntent {
    data object OnClickBack : CardBoughtIntent

    @Parcelize
    data class OnClickBook(
        val bookUIData: BookUIData
    ) : Parcelable, CardBoughtIntent
}

data class CardBoughtState(
    val booksList: List<BookUIData> = emptyList()
)
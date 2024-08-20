package com.example.assaxiybookcompose.presenter.ui.screen.main.page.audios

import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.CategoryByBooksUIData
import kotlinx.coroutines.flow.StateFlow

interface AudiosViewModel {
    val audioState: StateFlow<AudioState>
    fun onEvent(intent: AudiosIntent)
}

sealed interface AudiosIntent {
    data class OnClickCategory(val categoryByBooksUIData: CategoryByBooksUIData) : AudiosIntent
    data class OnClickBook(val bookUIData: BookUIData) : AudiosIntent
}

data class AudioState(
    val progressState: Boolean = false,
    val audioListData: List<CategoryByBooksUIData> = emptyList()
)
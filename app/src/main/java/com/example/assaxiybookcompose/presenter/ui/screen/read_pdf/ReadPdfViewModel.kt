package com.example.assaxiybookcompose.presenter.ui.screen.read_pdf

import kotlinx.coroutines.flow.StateFlow

interface ReadPdfViewModel {
    val uiState : StateFlow<ReadPdfState>
    fun setBookUrl(bookUrl: String)
    fun onEvent(intent: ReadPdfIntent)
}

sealed interface ReadPdfIntent {
    data object OnClickBack : ReadPdfIntent
}
data class ReadPdfState(
    val bookUrl : String = ""
)
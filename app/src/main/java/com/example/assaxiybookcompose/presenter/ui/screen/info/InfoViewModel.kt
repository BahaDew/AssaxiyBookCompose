package com.example.assaxiybookcompose.presenter.ui.screen.info

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.example.assaxiybookcompose.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface InfoViewModel {
    val uiState: StateFlow<InfoState>
    fun setBookUiData(bookUIData: BookUIData)
    fun onEvent(intent: InfoIntent)
}

@Stable
@Parcelize
data class InfoState(
    val bookUIData: BookUIData = BookUIData(),
    val isDownload: Boolean = false,
    val isLoading: Boolean = false,
    val bookPath: String = "",
    val openDefaultPdfReader: Boolean = false
) : Parcelable

@Stable
sealed interface InfoIntent {
    data object OnClickRead : InfoIntent
    data object OnClickBack : InfoIntent
    data object NotReadDefaultPdfReader : InfoIntent
    data object OpenPdfReader : InfoIntent
}
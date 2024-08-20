package com.example.assaxiybookcompose.presenter.ui.screen.category_by_audio

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.CategoryByBooksUIData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface CategoryByAudioViewModel {
    val uiState: StateFlow<CategoryAudiosState>

    fun onEventDispatcher(intent: CategoryAudiosIntent)
}

@Stable
sealed interface CategoryAudiosIntent {
    data object OnClickBack : CategoryAudiosIntent

    @Parcelize
    data class OnClickAudio(
        val bookUIData: BookUIData
    ) : Parcelable, CategoryAudiosIntent
}

@Stable
@Parcelize
data class CategoryAudiosState(
    val categoryAudios: CategoryByBooksUIData = CategoryByBooksUIData()
) : Parcelable
package com.example.assaxiybookcompose.presenter.ui.screen.category_by_books

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.CategoryByBooksUIData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface CategoryByBooksViewModel {
    val uiState : StateFlow<CategoryBookState>

    fun onEventDispatcher(intent: CategoryBooksIntent)
}

@Stable
sealed interface CategoryBooksIntent {
    data object OnClickBack : CategoryBooksIntent

    @Parcelize
    data class OnClickBook(
        val bookUIData: BookUIData
    ) : Parcelable, CategoryBooksIntent
}

@Stable
@Parcelize
data class CategoryBookState(
    val categoryBooks: CategoryByBooksUIData = CategoryByBooksUIData()
) : Parcelable
package com.example.assaxiybookcompose.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryByBooksUIData(
    val categoryId: String = "",
    val categoryName: String = "",
    val books: List<BookUIData> = emptyList(),
    val type: Int = 0
) : Parcelable
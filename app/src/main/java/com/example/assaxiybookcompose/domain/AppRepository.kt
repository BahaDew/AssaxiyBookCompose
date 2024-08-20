package com.example.assaxiybookcompose.domain

import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.CategoryByBooksUIData
import com.example.assaxiybookcompose.data.model.UserData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AppRepository {

    fun getCategoryByAudioBooks(): Flow<Result<List<CategoryByBooksUIData>>>

    fun getCategoryByPdfBooks(): Flow<Result<List<CategoryByBooksUIData>>>

    fun getBooksByName(name: String): Flow<Result<List<BookUIData>>>

    fun getDownloadAudioBooksData(): Flow<Result<List<BookUIData>>>

    fun getDownloadPdfBooksData(): Flow<Result<List<BookUIData>>>

    fun getUserBoughtBooks(): Flow<Result<List<BookUIData>>>

    fun loginUser(password: String, gmail: String): Flow<Result<Boolean>>

    fun registerUser(name: String, gmail: String, password: String): Flow<Result<Unit>>

    fun getIntroState(): Boolean

    fun getLoginState(): Boolean

    fun getUserData(): UserData

    fun isBought(bookUIData: BookUIData): Boolean

    fun isDownload(bookUIData: BookUIData): Flow<Boolean>

    fun getBookPath(bookUIData: BookUIData): Flow<Result<String>>

    fun buyBook(book: BookUIData): Flow<Result<String>>

    fun getAudioPath(bookUIData: BookUIData) : Flow<Result<String>>

    fun logOut()
}
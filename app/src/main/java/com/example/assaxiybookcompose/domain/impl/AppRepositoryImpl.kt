package com.example.assaxiybookcompose.domain.impl

import android.os.Environment
import com.example.assaxiybookcompose.data.local.dao.BookDao
import com.example.assaxiybookcompose.data.model.AddUserData
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.CategoryByBooksUIData
import com.example.assaxiybookcompose.data.model.UserData
import com.example.assaxiybookcompose.data.source.MySharedPreference
import com.example.assaxiybookcompose.data.toBookUIData
import com.example.assaxiybookcompose.data.toEntityBookData
import com.example.assaxiybookcompose.data.toUserDataList
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.utils.bahaLogger
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val mySharedPreference: MySharedPreference,
    private val bookDao: BookDao
) : AppRepository {

    private val fireStore = Firebase.firestore
    private val storage = Firebase.storage

    override fun getCategoryByAudioBooks(): Flow<Result<List<CategoryByBooksUIData>>> =
        callbackFlow<Result<List<CategoryByBooksUIData>>> {
            val categoryList = ArrayList<CategoryByBooksUIData>()
            var count = 0
            fireStore.collection("category")
                .addSnapshotListener { value, _ ->
                    value?.forEach {
                        val categoryName = it.data.getOrDefault("name", "") as String
                        val categoryId = it.id
                        fireStore.collection("books_data")
                            .whereEqualTo("categoryId", categoryId)
                            .get()
                            .addOnSuccessListener { qs ->
                                count++
                                val listBookData = ArrayList<BookUIData>()
                                qs.forEach { snapshot ->
                                    val type = snapshot.data.getOrDefault("type", "")
                                        .toString()
                                    if (type == "mp3") {
                                        val data =
                                            BookUIData(
                                                docID = snapshot.id,
                                                audioUrl = snapshot.data.getOrDefault(
                                                    "audioUrl",
                                                    ""
                                                )
                                                    .toString(),
                                                author = snapshot.data.getOrDefault("author", "")
                                                    .toString(),
                                                bookUrl = snapshot.data.getOrDefault("bookUrl", "")
                                                    .toString(),
                                                categoryId = snapshot.data.getOrDefault(
                                                    "categoryId",
                                                    ""
                                                )
                                                    .toString(),
                                                coverImage = snapshot.data.getOrDefault(
                                                    "coverImage",
                                                    ""
                                                )
                                                    .toString(),
                                                description = snapshot.data.getOrDefault(
                                                    "description",
                                                    ""
                                                )
                                                    .toString(),
                                                filePath = snapshot.data.getOrDefault(
                                                    "filePath",
                                                    ""
                                                )
                                                    .toString(),
                                                name = snapshot.data.getOrDefault("name", "")
                                                    .toString(),
                                                totalSize = snapshot.data.getOrDefault(
                                                    "totalSize",
                                                    ""
                                                )
                                                    .toString(),
                                                type = type,
                                            )
                                        listBookData.add(data)
                                    }
                                }
                                if (listBookData.isNotEmpty()) {
                                    categoryList.add(
                                        CategoryByBooksUIData(
                                            categoryName = categoryName,
                                            categoryId = categoryId,
                                            books = listBookData,
                                            type = 10
                                        )
                                    )
                                }
                                if (count == value.size()) {
                                    trySend(Result.success(categoryList))
                                }
                            }
                            .addOnFailureListener { exp ->
                                trySend(Result.failure(exp))
                            }
                    }
                }
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun getCategoryByPdfBooks(): Flow<Result<List<CategoryByBooksUIData>>> =
        callbackFlow<Result<List<CategoryByBooksUIData>>> {
            val categoryList = ArrayList<CategoryByBooksUIData>()
            var count = 0
            fireStore.collection("category")
                .addSnapshotListener { value, _ ->
                    value?.forEach {
                        val categoryName = it.data.getOrDefault("name", "") as String
                        val categoryId = it.id
                        fireStore.collection("books_data")
                            .whereEqualTo("categoryId", categoryId)
                            .get()
                            .addOnSuccessListener { qs ->
                                count++
                                val listBookData = ArrayList<BookUIData>()
                                qs.forEach { snapshot ->
                                    val type = snapshot.data.getOrDefault("type", "").toString()
                                    if (type == "pdf") {
                                        val data =
                                            BookUIData(
                                                docID = snapshot.id,
                                                audioUrl = snapshot.data.getOrDefault(
                                                    "audioUrl",
                                                    ""
                                                ).toString(),
                                                author = snapshot.data.getOrDefault("author", "")
                                                    .toString(),
                                                bookUrl = snapshot.data.getOrDefault("bookUrl", "")
                                                    .toString(),
                                                categoryId = snapshot.data.getOrDefault(
                                                    "categoryId",
                                                    ""
                                                )
                                                    .toString(),
                                                coverImage = snapshot.data.getOrDefault(
                                                    "coverImage",
                                                    ""
                                                )
                                                    .toString(),
                                                description = snapshot.data.getOrDefault(
                                                    "description",
                                                    ""
                                                )
                                                    .toString(),
                                                filePath = snapshot.data.getOrDefault(
                                                    "filePath",
                                                    ""
                                                )
                                                    .toString(),
                                                name = snapshot.data.getOrDefault("name", "")
                                                    .toString(),
                                                totalSize = snapshot.data.getOrDefault(
                                                    "totalSize",
                                                    ""
                                                )
                                                    .toString(),
                                                type = type,
                                            )
                                        listBookData.add(data)
                                    }
                                }
                                if (listBookData.isNotEmpty()) {
                                    categoryList.add(
                                        CategoryByBooksUIData(
                                            categoryName = categoryName,
                                            categoryId = categoryId,
                                            books = listBookData,
                                            type = 10
                                        )
                                    )
                                }
                                if (count == value.size()) {
                                    trySend(Result.success(categoryList))
                                }
                            }
                            .addOnFailureListener { exp ->
                                trySend(Result.failure(exp))
                            }
                    }
                }
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun getBooksByName(name: String): Flow<Result<List<BookUIData>>> =
        channelFlow<Result<List<BookUIData>>> {
            if (name.trim().isEmpty())
                trySend(Result.success(arrayListOf()))
            else {
                fireStore.collection("books_data")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val booksList = ArrayList<BookUIData>()
                        querySnapshot.forEach { snapshot ->
                            val bookName = snapshot.data.getOrDefault("name", "").toString()
                            if (bookName.lowercase().contains(name.lowercase())) {
                                val data =
                                    BookUIData(
                                        docID = snapshot.id,
                                        audioUrl = snapshot.data.getOrDefault("audioUrl", "")
                                            .toString(),
                                        author = snapshot.data.getOrDefault("author", "")
                                            .toString(),
                                        bookUrl = snapshot.data.getOrDefault("bookUrl", "")
                                            .toString(),
                                        categoryId = snapshot.data.getOrDefault(
                                            "categoryId",
                                            ""
                                        )
                                            .toString(),
                                        coverImage = snapshot.data.getOrDefault(
                                            "coverImage",
                                            ""
                                        )
                                            .toString(),
                                        description = snapshot.data.getOrDefault(
                                            "description",
                                            ""
                                        )
                                            .toString(),
                                        filePath = snapshot.data.getOrDefault("filePath", "")
                                            .toString(),
                                        name = bookName,
                                        totalSize = snapshot.data.getOrDefault("totalSize", "")
                                            .toString(),
                                        type = snapshot.data.getOrDefault("type", "")
                                            .toString(),
                                    )
                                booksList.add(data)
                            }
                        }
                        trySend(Result.success(booksList))
                    }
                    .addOnFailureListener { ex ->
                        trySend(Result.failure(ex))
                    }
            }
            awaitClose()
        }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }

    override fun getDownloadAudioBooksData(): Flow<Result<List<BookUIData>>> =
        callbackFlow {
            trySend(Result.success(bookDao.getAllBooks().filter { it.type == "mp3" }
                .map { it.toBookUIData() }))
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun getDownloadPdfBooksData(): Flow<Result<List<BookUIData>>> =
        callbackFlow {
            trySend(Result.success(bookDao.getAllBooks().filter { it.type == "pdf" }
                .map { it.toBookUIData() }))
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun getUserBoughtBooks(): Flow<Result<List<BookUIData>>> =
        callbackFlow<Result<List<BookUIData>>> {
            val bookList = ArrayList<BookUIData>()
            var count = 0
            val booksId = mySharedPreference.getUserData().booksId
            booksId.forEach { bookId ->
                count++
                fireStore.collection("books_data")
                    .get()
                    .addOnSuccessListener { qs ->
                        qs.forEach { snapshot ->
                            if (bookId == snapshot.id) {
                                val data =
                                    BookUIData(
                                        docID = snapshot.id,
                                        audioUrl = snapshot.data.getOrDefault("audioUrl", "")
                                            .toString(),
                                        author = snapshot.data.getOrDefault("author", "")
                                            .toString(),
                                        bookUrl = snapshot.data.getOrDefault("bookUrl", "")
                                            .toString(),
                                        categoryId = snapshot.data.getOrDefault(
                                            "categoryId",
                                            ""
                                        )
                                            .toString(),
                                        coverImage = snapshot.data.getOrDefault(
                                            "coverImage",
                                            ""
                                        )
                                            .toString(),
                                        description = snapshot.data.getOrDefault(
                                            "description",
                                            ""
                                        )
                                            .toString(),
                                        filePath = snapshot.data.getOrDefault("filePath", "")
                                            .toString(),
                                        name = snapshot.data.getOrDefault("name", "")
                                            .toString(),
                                        totalSize = snapshot.data.getOrDefault("totalSize", "")
                                            .toString(),
                                        type = snapshot.data.getOrDefault("type", "")
                                            .toString(),
                                    )
                                bookList.add(data)
                            }
                        }
                        if (count == booksId.size) {
                            trySend(Result.success(bookList))
                        }
                    }
                    .addOnFailureListener { ex ->
                        trySend(Result.failure(ex))
                    }
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun loginUser(password: String, gmail: String): Flow<Result<Boolean>> =
        callbackFlow {
            fireStore
                .collection("users")
                .whereEqualTo("password", password)
                .whereEqualTo("gmail", gmail).limit(1)
                .addSnapshotListener { value, error ->
                    if (value?.documents?.size == 0) {
                        trySend(Result.failure(Throwable("Bunaqa user mavjud emas !")))
                    } else {
                        val user = value!!.toUserDataList()[0]
                        mySharedPreference.setUserData(user)
                        mySharedPreference.putLoginState(true)
                        trySend(Result.success(true))
                        channel.close()
                    }

                    if (error != null) {
                        trySend(Result.success(false))
                        channel.close()
                    }
                }

            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun registerUser(name: String, gmail: String, password: String): Flow<Result<Unit>> =
        callbackFlow<Result<Unit>> {
            if (name.length <= 3 || password.length != 8 || gmail.length < 3) trySend(
                Result.failure(
                    Throwable("Ma'lumotlar to'g'ri to'ldirilmagan")
                )
            ) else {
                fireStore
                    .collection("users")
                    .add(AddUserData(name, gmail, password))
                    .addOnSuccessListener {
                        trySend(Result.success(Unit))
                        mySharedPreference.putLoginState(true)
                    }.addOnFailureListener {
                        trySend(Result.failure(it))
                    }
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun getIntroState(): Boolean {
        val oldState = mySharedPreference.getIntroState()
        if (!oldState) {
            mySharedPreference.putIntroState(true)
        }
        return oldState
    }

    override fun getLoginState(): Boolean {
        return mySharedPreference.getLoginState()
    }

    override fun getUserData(): UserData {
        return mySharedPreference.getUserData()
    }

    override fun logOut() {
        mySharedPreference.logOut()
        mySharedPreference.putLoginState(false)
    }

    override fun isBought(bookUIData: BookUIData): Boolean {
        return mySharedPreference.getUserData().booksId.contains(bookUIData.docID)
    }

    override fun isDownload(bookUIData: BookUIData): Flow<Boolean> = callbackFlow {
        val bookId = bookDao.isHas(bookUIData.bookUrl)
        bahaLogger(" download id $bookId bookurl = ${bookUIData.bookUrl}")
        trySend(bookId != 0L)
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun getBookPath(bookUIData: BookUIData): Flow<Result<String>> =
        callbackFlow<Result<String>> {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
                bookUIData.name + ".pdf"
            )
            if (file.exists()) {
                trySend(Result.success(file.toString()))
            } else {
                trySend(Result.failure(Throwable("Kitob mavjud emas")))
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override fun buyBook(book: BookUIData): Flow<Result<String>> = callbackFlow<Result<String>> {
        val user = mySharedPreference.getUserData()
        user.booksId.add(book.docID)
        mySharedPreference.setUserData(user)
        fireStore
            .collection("users")
            .document(user.id)
            .set(user)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
            }
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).absolutePath,
            book.name + ".pdf"
        )
        bookDao.setBook(book.toEntityBookData(path = file.absolutePath))
        storage
            .getReferenceFromUrl(book.bookUrl)
            .getFile(file)
            .addOnSuccessListener {
                trySend(Result.success(file.absolutePath))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun getAudioPath(bookUIData: BookUIData): Flow<Result<String>> =
        callbackFlow<Result<String>> {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
                "${bookUIData.name}.mp3"
            )
            if (file.exists()) {
                trySend(Result.success(file.absolutePath))
            } else {
                bahaLogger("audio url -> " + bookUIData.bookUrl)
                storage
                    .getReferenceFromUrl(bookUIData.bookUrl)
                    .getFile(file)
                    .addOnSuccessListener {
                        trySend(Result.success(file.absolutePath))
                    }
                    .addOnFailureListener {
                        trySend(Result.failure(it))
                    }
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
}
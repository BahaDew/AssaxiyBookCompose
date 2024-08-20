package com.example.assaxiybookcompose.data

import com.example.assaxiybookcompose.data.local.entitiy.EntityBookData
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.UserData
import com.google.firebase.firestore.QuerySnapshot

fun EntityBookData.toBookUIData() : BookUIData{
    return BookUIData(
        docID = this.docID,
        audioUrl = this.audioUrl,
        author = this.author,
        bookUrl = this.bookUrl,
        categoryId = this.categoryId,
        coverImage = this.coverImage,
        description = this.description,
        filePath = this.filePath,
        name = this.name,
        totalSize = this.totalSize,
        type = this.type
    )
}
fun QuerySnapshot.toUserDataList(): List<UserData> {
    val userList = mutableListOf<UserData>()
    for (document in this) {
        val id = document.id
        val name = document.data.getOrDefault("name", "").toString()
        val gmail = document.data.getOrDefault("gmail", "").toString()
        val password = document.data.getOrDefault("password", "").toString()
        val booksId = document.data.getOrDefault("booksId", mutableListOf<String>()) as List<String>
        val userData = UserData(
            id = id,
            name = name,
            gmail = gmail,
            password = password,
            booksId = booksId.toMutableList() as ArrayList<String>
        )
        userList.add(userData)
    }
    return userList
}

fun BookUIData.toEntityBookData(path: String): EntityBookData {
    return EntityBookData(
        id = 0,
        docID = this.docID,
        audioUrl = this.audioUrl,
        author = this.author,
        bookUrl = this.bookUrl,
        categoryId = this.categoryId,
        coverImage = this.coverImage,
        description = this.description,
        filePath = path,
        name = this.name,
        totalSize = this.totalSize,
        type = this.type
    )
}
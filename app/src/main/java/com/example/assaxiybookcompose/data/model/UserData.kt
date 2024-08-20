package com.example.assaxiybookcompose.data.model

data class UserData (
    val id: String = "",
    val name: String = "",
    val gmail: String = "",
    val password: String = "",
    val booksId: ArrayList<String> = arrayListOf()
)
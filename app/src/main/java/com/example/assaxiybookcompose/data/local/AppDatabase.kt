package com.example.assaxiybookcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assaxiybookcompose.data.local.dao.BookDao
import com.example.assaxiybookcompose.data.local.entitiy.EntityBookData

@Database(entities = [EntityBookData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getBookDao() : BookDao
}
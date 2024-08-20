package com.example.assaxiybookcompose.di

import android.content.Context
import androidx.room.Room
import com.example.assaxiybookcompose.data.local.AppDatabase
import com.example.assaxiybookcompose.data.local.dao.BookDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @[Provides Singleton]
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "Assaxiy.db")
            .build()
    }

    @[Provides Singleton]
    fun provideBookDao(appDatabase: AppDatabase): BookDao {
        return appDatabase.getBookDao()
    }

}
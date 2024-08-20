package com.example.assaxiybookcompose.di

import android.content.Context
import com.example.assaxiybookcompose.data.source.MySharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedModule {

    @[Provides Singleton]
    fun provideShared(@ApplicationContext context: Context): MySharedPreference {
        return MySharedPreference(context.getSharedPreferences("AssaxiyBook", Context.MODE_PRIVATE))
    }
}
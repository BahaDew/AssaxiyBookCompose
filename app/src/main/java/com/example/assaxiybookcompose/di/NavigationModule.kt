package com.example.assaxiybookcompose.di

import com.example.assaxiybookcompose.navigation.AppNavigationDispatcher
import com.example.assaxiybookcompose.navigation.AppNavigationHandler
import com.example.assaxiybookcompose.navigation.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun bindAppNavigator(dispatcher: AppNavigationDispatcher) : AppNavigator

    @[Binds Singleton]
    fun bindAppNavigationHandler(dispatcher: AppNavigationDispatcher) : AppNavigationHandler
}
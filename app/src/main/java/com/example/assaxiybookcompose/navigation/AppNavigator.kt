package com.example.assaxiybookcompose.navigation

import cafe.adriel.voyager.core.screen.Screen

interface AppNavigator {

    suspend fun push(screen : AppScreen)

    suspend fun replace(screen: AppScreen)

    suspend fun replaceAll(screen: AppScreen)

    suspend fun popUntil(screen: AppScreen)

    suspend fun pop()

    suspend fun popAll()
}

typealias AppScreen = Screen
package com.example.assaxiybookcompose.navigation

import kotlinx.coroutines.flow.Flow

interface AppNavigationHandler {
    val buffer : Flow<AppNavigation>
}
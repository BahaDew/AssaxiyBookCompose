package com.example.assaxiybookcompose.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationDispatcher @Inject constructor() : AppNavigator, AppNavigationHandler {
    override val buffer = MutableSharedFlow<AppNavigation>()

    private suspend fun navigate(appNavigation: AppNavigation) {
        buffer.emit(appNavigation)
    }

    override suspend fun push(screen: AppScreen) = navigate {
        this.push(screen)
    }

    override suspend fun replace(screen: AppScreen) = navigate {
        this.replace(screen)
    }

    override suspend fun replaceAll(screen: AppScreen) = navigate {
        this.replaceAll(screen)
    }

    override suspend fun popUntil(screen: AppScreen) = navigate {
        this.popUntil { it == screen }
    }

    override suspend fun pop() = navigate {
        this.pop()
    }

    override suspend fun popAll() = navigate {
        this.popAll()
    }
}
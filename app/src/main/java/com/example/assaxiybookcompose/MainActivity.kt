package com.example.assaxiybookcompose

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.assaxiybookcompose.navigation.AppNavigationHandler
import com.example.assaxiybookcompose.presenter.ui.screen.splash.SplashScreen
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.utils.bahaLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigationHandler: AppNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssaxiyBookComposeTheme {
                Navigator(screen = SplashScreen()) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        appNavigationHandler.buffer
                            .onEach { it(navigator) }
                            .launchIn(lifecycleScope)
                    }
                    CurrentScreen()
                }
            }
        }
        bahaLogger("Permission ${checkPermissionForReadExtertalStorage()}")
        requestPermissionForReadExtertalStorage()
    }
    private fun checkPermissionForReadExtertalStorage(): Boolean {
        val result = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1000
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
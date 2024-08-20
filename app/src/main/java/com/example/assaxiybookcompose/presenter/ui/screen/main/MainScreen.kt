package com.example.assaxiybookcompose.presenter.ui.screen.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.main.page.audios.AudiosTab
import com.example.assaxiybookcompose.presenter.ui.screen.main.page.books.BooksTab
import com.example.assaxiybookcompose.presenter.ui.screen.main.page.library.LibraryTab
import com.example.assaxiybookcompose.presenter.ui.screen.main.page.profile.ProfileTab
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.NoActive


class MainScreen : Screen {
    @Composable
    override fun Content() {
        ScreenContent()
    }
}

@Composable
fun ScreenContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        TabNavigator(
            tab = BooksTab
        ) {
            Scaffold(
                content = {
                    it
                    CurrentTab()
                },
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = AppBgColor
                    ) {
                        TabNavigationItem(tab = BooksTab)
                        TabNavigationItem(tab = LibraryTab)
                        TabNavigationItem(tab = AudiosTab)
                        TabNavigationItem(tab = ProfileTab)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent()
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    BottomNavigationItem(
        selectedContentColor = Active,
        unselectedContentColor = NoActive,
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            val isSelect = tabNavigator.current == tab
            Icon(
                painter = tab.options.icon!!,
                contentDescription = tab.options.title,
                tint = if (isSelect) Active else NoActive
            )
        },
        label = {
            val isSelect = tabNavigator.current == tab
            Text(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                text = tab.options.title,
                fontSize = 9.sp,
                color = if (isSelect) Active else NoActive,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        },
        alwaysShowLabel = true
    )
}
package com.example.assaxiybookcompose.presenter.ui.screen.main.page.books

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.screen.main.page.library.LibraryTab
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.SecondaryTextColor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object BooksTab : Tab {
    private fun readResolve(): Any = BooksTab
    override val options: TabOptions
        @Composable
        get() {
            val title = "Kitoblarim"
            val icon = painterResource(id = R.drawable.ic_menu_book)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: BooksViewModel = getViewModel<BooksViewModelImpl>()
        val uiState = viewModel.uiState


        LaunchedEffect(key1 = Unit) {
            uiState
                .onEach {

                }.launchIn(this)
        }
        ScreenContent(
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun ScreenContent(
    onEvent: (BookIntent) -> Unit
) {
    val tabNavigator = LocalTabNavigator.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBgColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppBgColor)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(AppBgColor)
                )
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(color = AppBgColor)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .align(Alignment.CenterStart),
                        text = "Mening kitoblarim",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(color = AppBgColor)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Image(
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                                .align(Alignment.CenterHorizontally),
                            painter = painterResource(id = R.drawable.plaseholder),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "Bu yerda siz o'qishni yoki tinglashni \nxohlagan kitoblaringiz ro'yhatini \ntopasiz",
                            color = SecondaryTextColor,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .align(Alignment.CenterHorizontally)
                                .background(color = Color.Transparent),
                            onClick = {
                                tabNavigator.current = LibraryTab
                            },
                            shape = RoundedCornerShape(20),
                            colors = ButtonDefaults.buttonColors(containerColor = Active)
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Kitoblarni qo'shish",
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(AppBgColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent(onEvent = {})
    }
}

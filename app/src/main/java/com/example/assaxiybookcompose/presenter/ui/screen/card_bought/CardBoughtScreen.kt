package com.example.assaxiybookcompose.presenter.ui.screen.card_bought

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.components.items.CategoryByIPdfAndAudioItem
import com.example.assaxiybookcompose.presenter.ui.components.items.TypeEnum
import com.example.assaxiybookcompose.presenter.ui.components.theme.CustomRippleColor
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import java.text.SimpleDateFormat
import java.util.Date

class CardBoughtScreen : Screen {
    @Composable
    override fun Content() {
        AssaxiyBookComposeTheme {
            val viewModel: CardBoughtViewModel = getViewModel<CardBoughtViewModelImpl>()
            val uiState = viewModel.uiState.collectAsState()
            ScreenContent(
                uiState = uiState,
                onEventDispatcher = viewModel::onEventDispatcher
            )
        }
    }
}

@Composable
fun ScreenContent(
    uiState: State<CardBoughtState>,
    onEventDispatcher: (CardBoughtIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBgColor)
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
                CompositionLocalProvider(LocalRippleTheme provides CustomRippleColor(color = Color.White)) {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clip(CircleShape)
                            .width(40.dp)
                            .height(40.dp)
                            .align(Alignment.CenterStart),
                        onClick = { onEventDispatcher.invoke(CardBoughtIntent.OnClickBack) }
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            painter = painterResource(id = R.drawable.ic_left_chevron_svgrepo_com),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Buyurtmalar tarixi",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                items(items = uiState.value.booksList) {
                    CategoryByIPdfAndAudioItem(
                        bookUIData = it,
                        typeEnum = TypeEnum.PDF,
                        onClickItem = { book ->
                            onEventDispatcher.invoke(CardBoughtIntent.OnClickBook(book))
                        }
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent(
            uiState = remember { mutableStateOf(value = CardBoughtState()) },
            onEventDispatcher = {}
        )
    }
}
package com.example.assaxiybookcompose.presenter.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColorSecondary
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SearchViewModel = getViewModel<SearchViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        AssaxiyBookComposeTheme {
            ScreenContent(
                uiState = uiState,
                onEventDispatcher = viewModel::onEventDispatcher
            )
        }
    }
}

@Composable
fun ScreenContent(
    uiState: State<SearchState>,
    onEventDispatcher: (SearchIntent) -> Unit
) {
    var textChangeState = rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBgColor)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                CompositionLocalProvider(
                    value = LocalRippleTheme provides CustomRippleColor(color = Color.White)
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(40.dp)
                            .height(40.dp)
                            .clip(CircleShape)
                            .clickable { onEventDispatcher.invoke(SearchIntent.OnClickBack) }
                            .align(Alignment.CenterVertically)
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.ic_left_chevron_svgrepo_com),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                }
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(30)),
                    value = textChangeState.value,
                    onValueChange = {
                        textChangeState.value = it
                        onEventDispatcher.invoke(SearchIntent.TextChange(text = it))
                    },
                    leadingIcon = {
                        Image(
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp),
                            painter = painterResource(id = R.drawable.ic_search_input),
                            contentDescription = ""
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = AppBgColorSecondary,
                        textColor = Color.White,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Kitoblarni qidirish",
                            color = Color(0xFF919191),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 15.dp)
            ) {
                items(items = uiState.value.resultBooksList) {
                    CategoryByIPdfAndAudioItem(
                        bookUIData = it,
                        typeEnum = TypeEnum.PDF,
                        onClickItem = { book ->
                            onEventDispatcher.invoke(SearchIntent.OnClickBook(book))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContent() {
    AssaxiyBookComposeTheme {
        ScreenContent(
            uiState = remember { mutableStateOf(SearchState()) },
            onEventDispatcher = {}
        )
    }
}
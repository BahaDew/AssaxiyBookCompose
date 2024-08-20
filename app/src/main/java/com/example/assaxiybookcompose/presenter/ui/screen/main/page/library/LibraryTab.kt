package com.example.assaxiybookcompose.presenter.ui.screen.main.page.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.AsyncImage
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.components.theme.CustomRippleColor
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.SecondaryTextColor

object LibraryTab : Tab {
    private fun readResolve(): Any = LibraryTab
    override val options: TabOptions
        @Composable
        get() {
            val title = "Kutubxona"
            val icon = painterResource(id = R.drawable.ic_menu_library)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: LibraryViewModel = getViewModel<LibraryViewModelImpl>()
        val uiState by viewModel.libraryState.collectAsState()
        ScreenContent(
            eventDispatcher = viewModel::libraryEvent,
            uiState = uiState
        )
    }
}

@Composable
fun ScreenContent(
    eventDispatcher: (LibraryIntent) -> Unit,
    uiState: LibraryState
) {
    AssaxiyBookComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBgColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
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
                        text = "Kutubxona",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )
                    CompositionLocalProvider(
                        value = LocalRippleTheme provides CustomRippleColor(
                            color = Color.White
                        )
                    ) {
                        IconButton(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .clip(CircleShape)
                                .width(45.dp)
                                .height(45.dp)
                                .align(Alignment.CenterEnd),
                            onClick = { }
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { eventDispatcher.invoke(LibraryIntent.OnClickSearch) }
                                    .padding(5.dp),
                                painter = painterResource(id = R.drawable.ic_search_input),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                        .weight(1f)
                ) {
                    items(uiState.libraryListData) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                text = it.categoryName,
                                fontSize = 23.sp,
                                color = Color.White
                            )
                            TextButton(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                onClick = {
                                    eventDispatcher.invoke(
                                        LibraryIntent.OnClickCategory(it)
                                    )
                                }
                            ) {
                                Text(
                                    text = "Hammasi",
                                    fontSize = 15.sp,
                                    color = Active
                                )
                            }
                        }
                        LazyRow(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        ) {
                            items(it.books) {
                                Column(
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .padding(start = 5.dp, end = 8.dp)
                                        .clickable {
                                            eventDispatcher.invoke(LibraryIntent.OnClickBook(it))
                                        }
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(6)),
                                        contentScale = ContentScale.Crop,
                                        model = it.coverImage,
                                        contentDescription = "",
                                        placeholder = painterResource(id = R.drawable.book),
                                        error = painterResource(id = R.drawable.ic_logo_1)
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 5.dp)
                                            .width(160.dp),
                                        text = it.name,
                                        color = Color.White,
                                        maxLines = 2,
                                        fontSize = 18.sp,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 5.dp)
                                            .width(160.dp)
                                            .height(50.dp),
                                        text = it.author,
                                        color = SecondaryTextColor,
                                        maxLines = 2,
                                        fontSize = 14.sp
                                    )
                                }
                            }
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
            if (uiState.progressState) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .align(Alignment.Center),
                    color = Color.White,
                    strokeWidth = 5.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent({}, LibraryState())
    }
}
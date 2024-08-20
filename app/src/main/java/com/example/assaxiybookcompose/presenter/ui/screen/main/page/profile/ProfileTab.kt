package com.example.assaxiybookcompose.presenter.ui.screen.main.page.profile

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.components.theme.CustomRippleColor
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.SecondaryTextColor

object ProfileTab : Tab {
    private fun readResolve(): Any = ProfileTab
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profil"
            val icon = painterResource(id = R.drawable.ic_menu_profile)
            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: ProfileViewModel = getViewModel<ProfileViewModelImpl>()
        val uiState by viewModel.uiState.collectAsState()
        AssaxiyBookComposeTheme {
            ScreenContent(
                eventDispatch = viewModel::onEvent,
                uiState = uiState
            )
        }
    }
}

data class ItemData(
    val icon: Int,
    val title: String
)

val itemList = arrayListOf(
    ItemData(
        icon = R.drawable.ic_users,
        title = "Sevimli avtorlar"
    ),
    ItemData(
        icon = R.drawable.ic_cart,
        title = "Buyurtmalar tarixi"
    ),
    ItemData(
        icon = R.drawable.ic_notification_profile,
        title = "Xabarnomalar"
    ),
    ItemData(
        icon = R.drawable.ic_kupon,
        title = "Kuponlar"
    ),
    ItemData(
        icon = R.drawable.ic_language,
        title = "Ilova tili"
    ),
    ItemData(
        icon = R.drawable.ic_night_mode,
        title = "Ilova mavzusi"
    ),
    ItemData(
        icon = R.drawable.ic_about,
        title = "Biz haqimizda"
    ),
    ItemData(
        icon = R.drawable.ic_call,
        title = "Biz bilan bog'lanish"
    )
)

@Composable
fun ScreenContent(
    eventDispatch: (ProfileIntent) -> Unit,
    uiState: ProfileState
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(30.dp)
                    .background(AppBgColor)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(AppBgColor)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .weight(1f)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .fillMaxWidth()
                                .background(color = AppBgColor)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                text = "Profil",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium))
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 20.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(58.dp)
                                    .height(58.dp)
                                    .clip(CircleShape),
                                painter = painterResource(id = R.drawable.img),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = uiState.userData.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                ) {
                                    Text(
                                        text = "Tahrirlash uchun ustiga bosing",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = SecondaryTextColor
                                    )
                                    Image(
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(start = 5.dp)
                                            .width(15.dp)
                                            .height(15.dp),
                                        painter = painterResource(id = R.drawable.ic_edit_profile),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 7.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF09273f),
                                                Color(0xFF537795)
                                            )
                                        ),
                                        shape = RoundedCornerShape(20)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Balans:",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 5.dp),
                                    text = "0 so'm",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 7.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF3d4e81),
                                                Color(0xFF5753c9),
                                                Color(0xFF6e7ff3),
                                            )
                                        ),
                                        shape = RoundedCornerShape(20)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Balans:",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 5.dp),
                                    text = "0 so'm",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFF172342),
                                    shape = RoundedCornerShape(20)
                                )
                                .padding(vertical = 10.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                text = "Hison raqam: A415154",
                                fontSize = 17.sp,
                                color = Color.White
                            )
                            Image(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                painter = painterResource(id = R.drawable.ic_copy),
                                contentDescription = ""
                            )
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20),
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Active
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 5.dp),
                                text = "Hisobni to'ldirish",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(color = Color(0xFF192647))
                        )
                        itemList.forEach {
                            CompositionLocalProvider(
                                LocalRippleTheme provides CustomRippleColor(Active)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 6.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(20))
                                        .clickable {
                                            if (it.icon == R.drawable.ic_cart) {
                                                eventDispatch.invoke(ProfileIntent.OnCLickCard)
                                            }
                                        }
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(40.dp)
                                            .background(
                                                color = Color(0xFF19274A),
                                                shape = RoundedCornerShape(20)
                                            )
                                            .padding(8.dp),
                                        painter = painterResource(id = it.icon),
                                        contentDescription = ""
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 15.dp)
                                            .align(Alignment.CenterVertically),
                                        text = it.title,
                                        color = Color.White,
                                        fontSize = 17.sp
                                    )
                                }
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(color = Color(0xFF192647))
                        )
                        CompositionLocalProvider(
                            LocalRippleTheme provides CustomRippleColor(
                                Color(0xFFFE5755)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20))
                                    .clickable {
                                        eventDispatch.invoke(ProfileIntent.OnCLickLogOut)
                                    }
                            ) {
                                Image(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .background(
                                            color = Color(0x1EE91E63),
                                            shape = RoundedCornerShape(20)
                                        )
                                        .padding(8.dp),
                                    painter = painterResource(id = R.drawable.ic_log_out),
                                    contentDescription = ""
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 15.dp)
                                        .align(Alignment.CenterVertically),
                                    text = "Akkauntdan chiqish",
                                    color = Color.White,
                                    fontSize = 17.sp
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                text = "assaxiy.uz All rights reserved \nv3.0.5",
                                fontSize = 18.sp,
                                color = Color(0x95FFFFFF),
                                textAlign = TextAlign.Center
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
        ScreenContent({}, ProfileState())
    }
}
package com.example.assaxiybookcompose.presenter.ui.screen.intro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.TypeColor
import com.example.assaxiybookcompose.utils.bahaLogger
import kotlinx.coroutines.launch

class IntroScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: IntroViewModel = getViewModel<IntroViewModelImpl>()
        ScreenContent(onClick = viewModel::onClickStart)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenContent(onClick: () -> Unit) {
    val pageState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    val imageList = arrayOf(
        R.drawable.introduction_1_night,
        R.drawable.introduction_2_night,
        R.drawable.introduction_3_night
    )
    val modifierUnSelect = Modifier
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .border(
            border = BorderStroke(
                width = 2.dp,
                color = TypeColor
            ), shape = CircleShape
        )
        .width(15.dp)
        .height(15.dp)
    val modifierSelect = Modifier
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .width(15.dp)
        .height(15.dp)
        .clip(CircleShape)
        .background(color = TypeColor)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBgColor)
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pageState,
                pageSize = PageSize.Fill,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = imageList[it]),
                    contentDescription = ""
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                for (i in 0..2) {
                    Spacer(
                        modifier =
                        if (i <= pageState.currentPage)
                            modifierSelect
                        else modifierUnSelect
                    )
                }
            }
            Button(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    if (pageState.currentPage < 2) {
                        scope.launch {
                            pageState.animateScrollToPage(pageState.currentPage + 1)
                        }
                    } else {
                        onClick.invoke()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Active),
                shape = RoundedCornerShape(30)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = if (pageState.currentPage >= 2) "Start" else "Next",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent({})
    }
}
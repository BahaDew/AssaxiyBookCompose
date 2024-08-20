package com.example.assaxiybookcompose.presenter.ui.screen.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.presenter.ui.components.theme.CustomRippleColor
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColorSecondary
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.SecondaryTextColor
import com.example.assaxiybookcompose.utils.bahaLogger
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize
import java.io.File

class InfoScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: InfoViewModel = getViewModel<InfoViewModelImpl>()
        val context = LocalContext.current
        val uiState by viewModel.uiState.collectAsState()
        if (uiState.openDefaultPdfReader) {
            bahaLogger("qayta qayta")
            val file = File(uiState.bookPath)
            val fileUri =
                FileProvider.getUriForFile(context, context.packageName + ".provider", file)
            bahaLogger(uiState.bookPath)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(fileUri, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            viewModel.onEvent(InfoIntent.OpenPdfReader)
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                viewModel.onEvent(InfoIntent.NotReadDefaultPdfReader)
            }
        }
        ScreenContent(viewModel::onEvent, uiState)
    }
}

@Composable
fun ScreenContent(
    eventDispatch: (InfoIntent) -> Unit,
    uiState: InfoState
) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBgColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
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
                    CompositionLocalProvider(
                        value = LocalRippleTheme provides CustomRippleColor(
                            color = Color.White
                        )
                    ) {

                        IconButton(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .align(Alignment.CenterStart),
                            onClick = { eventDispatch.invoke(InfoIntent.OnClickBack) }
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
                }
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(220.dp)
                            .height(300.dp)
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(10)),
                        model = uiState.bookUIData.coverImage,
                        contentDescription = "",
                        placeholder = painterResource(id = R.drawable.book),
                        error = painterResource(id = R.drawable.ic_logo_1),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .align(Alignment.CenterHorizontally),
                        text = uiState.bookUIData.name,
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = uiState.bookUIData.author,
                        fontSize = 20.sp,
                        color = SecondaryTextColor,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        shape = RoundedCornerShape(15),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Active
                        ),
                        onClick = { eventDispatch.invoke(InfoIntent.OnClickRead) }
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                strokeWidth = 4.dp,
                                color = Color.White
                            )
                        } else {
                            bahaLogger("ui state ${uiState.isDownload}")
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 5.dp),
                                text = if (!uiState.isDownload) "Yuklab olish" else "Kitobni o'qish",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 15.dp),
                        text = "Kitob haqida",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .background(
                                color = AppBgColorSecondary,
                                shape = RoundedCornerShape(30)
                            )
                            .padding(vertical = 5.dp, horizontal = 10.dp)

                    ) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.ic_about),
                            contentDescription = ""
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 15.dp),
                            text = "Books 3",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp),
                        text = uiState.bookUIData.description,
                        color = SecondaryTextColor,
                        fontSize = 20.sp
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
        ScreenContent({}, InfoState())
    }
}
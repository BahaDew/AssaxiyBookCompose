package com.example.assaxiybookcompose.presenter.ui.screen.read_audio

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.components.theme.CustomRippleColor
import com.example.assaxiybookcompose.presenter.ui.theme.Active
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.presenter.ui.theme.SecondaryTextColor
import com.example.assaxiybookcompose.utils.audioMediaPlayer
import com.example.assaxiybookcompose.utils.bahaLogger
import java.text.SimpleDateFormat
import java.util.Date

class ReadAudioScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: ReadAudioViewModel = getViewModel<ReadAudioViewModelImpl>()
        val uiState = viewModel.uiState.collectAsState()
        val localContext = LocalContext.current
        ScreenContent(
            uiState = uiState,
            onEventDispatcher = viewModel::onEventDispatcher
        )
        if (uiState.value.mediaPath != null) {
            audioMediaPlayer?.stop()
            audioMediaPlayer = MediaPlayer.create(
                localContext,
                Uri.parse(uiState.value.mediaPath)
            )
            viewModel.onEventDispatcher(ReadAudioIntent.LoadMusic)
            audioMediaPlayer?.start()
            hearMusic(
                progress = { current, max ->
                    viewModel.onEventDispatcher(
                        ReadAudioIntent.AudioProgress(
                            current = current,
                            max = max
                        )
                    )
                },
                finish = {
                    hearMusic(
                        progress = { current, max ->
                            viewModel.onEventDispatcher(
                                ReadAudioIntent.AudioProgress(
                                    current = current,
                                    max = max
                                )
                            )
                        },
                        finish = {

                        }
                    )
                }
            )
        }
        SideEffect {
            if (uiState.value.isPauseAudio) {
                audioMediaPlayer?.pause()
            } else {
                audioMediaPlayer?.start()
            }
        }
        LaunchedEffect(key1 = uiState.value.tickProgress) {
            audioMediaPlayer?.seekTo(uiState.value.tickProgress.toInt())
        }
    }

    private fun hearMusic(
        progress: (current: Float, max: Float) -> Unit,
        finish: () -> Unit
    ) {
        val time = object : CountDownTimer(audioMediaPlayer!!.duration.toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                progress.invoke(
                    audioMediaPlayer!!.currentPosition.toFloat(),
                    audioMediaPlayer!!.duration.toFloat()
                )
            }

            override fun onFinish() {
                finish.invoke()
            }
        }
        time.start()
    }
}

@Composable
fun ScreenContent(
    uiState: State<ReadAudioState>,
    onEventDispatcher: (ReadAudioIntent) -> Unit
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
                        onClick = { onEventDispatcher.invoke(ReadAudioIntent.OnClickBack) }
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
            AsyncImage(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 15.dp)
                    .clip(RoundedCornerShape(6))
                    .fillMaxWidth()
                    .height(400.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                model = uiState.value.bookUIData.coverImage,
                contentDescription = "",
                error = painterResource(id = R.drawable.ic_logo_1),
                placeholder = painterResource(id = R.drawable.book),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
                text = uiState.value.bookUIData.name,
                color = Color.White,
                fontSize = 20.sp,
                maxLines = 1,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
                text = uiState.value.bookUIData.author,
                color = SecondaryTextColor,
                fontSize = 17.sp,
                maxLines = 1,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            ) {
                Slider(
                    modifier = Modifier,
                    value = uiState.value.progressState,
                    onValueChange = {
                        onEventDispatcher.invoke(
                            ReadAudioIntent.ToProgress(
                                current = it
                            )
                        )
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTickColor = Color.White,
                        inactiveTickColor = Color.White,
                        inactiveTrackColor = Color(0x94FFFFFF),
                        activeTrackColor = Active
                    ),
                    valueRange = 0f..uiState.value.maxProgress
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart),
                        text = toDataFormat(uiState.value.progressState.toLong()),
                        color = SecondaryTextColor,
                        fontSize = 16.sp
                    )
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd),
                        text = toDataFormat(uiState.value.maxProgress.toLong()),
                        color = SecondaryTextColor,
                        fontSize = 16.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 40.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .width(40.dp)
                        .height(40.dp)
                        .clickable { },
                    painter = painterResource(id = R.drawable.ic_prev),
                    contentDescription = ""
                )
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .width(40.dp)
                        .height(40.dp)
                        .clip(CircleShape)
                        .clickable { onEventDispatcher.invoke(ReadAudioIntent.OnClickPausePlay) },
                    painter = painterResource(
                        id =
                        if (uiState.value.isPauseAudio) R.drawable.ic_play
                        else R.drawable.ic_pause
                    ),
                    contentDescription = ""
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .width(40.dp)
                        .height(40.dp)
                        .clickable { },
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = ""
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun toDataFormat(millis: Long): String {
    val formatter = SimpleDateFormat("mm:ss")
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent(
            uiState = remember { mutableStateOf(ReadAudioState()) },
            onEventDispatcher = {}
        )
    }
}
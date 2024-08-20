package com.example.assaxiybookcompose.presenter.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SplashViewModel = getViewModel<SplashViewModelImpl>()
        LaunchedEffect(key1 = Unit) {
            viewModel.openSplash()
        }
        ScreenContent()
    }
}

@Composable
fun ScreenContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            val preloaderLottieComposition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(
                    R.raw.asaxiy_logo
                )
            )
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.gradient),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            LottieAnimation(
                modifier = Modifier
                    .width(400.dp)
                    .height(400.dp)
                    .align(Alignment.Center),
                composition = preloaderLottieComposition,
                iterations = LottieConstants.IterateForever
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
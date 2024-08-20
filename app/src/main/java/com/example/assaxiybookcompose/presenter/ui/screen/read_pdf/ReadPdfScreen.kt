package com.example.assaxiybookcompose.presenter.ui.screen.read_pdf

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColor
import com.example.assaxiybookcompose.presenter.ui.theme.AssaxiyBookComposeTheme
import com.example.assaxiybookcompose.utils.bahaLogger
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState

class ReadPdfScreen(val bookUrl: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ReadPdfViewModel = getViewModel<ReadPdfViewModelImpl>()
        viewModel.setBookUrl(bookUrl = bookUrl)
        val uiState by viewModel.uiState.collectAsState()

        ScreenContent(
            eventDispatch = viewModel::onEvent,
            uiState = uiState
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ScreenContent(
    eventDispatch: (ReadPdfIntent) -> Unit,
    uiState: ReadPdfState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val verticalPdfState = VerticalPdfReaderState(
            resource = ResourceType.Local(Uri.parse("file:///" + uiState.bookUrl)),
            isZoomEnable = true
        )
        LaunchedEffect(key1 = verticalPdfState.error) {
            bahaLogger(verticalPdfState.error?.message ?: " nomalum xatolik")
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(AppBgColor)
                .align(Alignment.CenterHorizontally)
        )
        VerticalPDFReader(
            state = verticalPdfState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBgColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AssaxiyBookComposeTheme {
        ScreenContent({}, ReadPdfState())
    }
}
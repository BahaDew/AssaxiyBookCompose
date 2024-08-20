package com.example.assaxiybookcompose.utils

import android.media.MediaPlayer
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.data.model.CategoryByBooksUIData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

val toInfo =
    MutableSharedFlow<BookUIData>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

val toCategoryPdf = MutableSharedFlow<CategoryByBooksUIData>(
    replay = 1,
    onBufferOverflow = BufferOverflow.DROP_LATEST
)
val toCategoryAudio = MutableSharedFlow<CategoryByBooksUIData>(
    replay = 1,
    onBufferOverflow = BufferOverflow.DROP_LATEST
)

val toReadAudio =
    MutableSharedFlow<BookUIData>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

var audioMediaPlayer: MediaPlayer? = null
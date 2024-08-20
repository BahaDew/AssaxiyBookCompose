package com.example.assaxiybookcompose.presenter.ui.screen.read_audio

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.example.assaxiybookcompose.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface ReadAudioViewModel {
    val uiState: StateFlow<ReadAudioState>
    fun onEventDispatcher(intent: ReadAudioIntent)
}

@Stable
sealed interface ReadAudioIntent {
    data object OnClickBack : ReadAudioIntent
    @Parcelize
    data class AudioProgress(
        val current : Float,
        val max : Float
    ) : Parcelable, ReadAudioIntent
    data object OnClickPausePlay : ReadAudioIntent
    data object LoadMusic : ReadAudioIntent
    @Parcelize
    data class ToProgress(
        val current : Float
    ) : Parcelable, ReadAudioIntent
}

@Stable
@Parcelize
data class ReadAudioState(
    val bookUIData: BookUIData = BookUIData(),
    var progressState : Float = 0f,
    val maxProgress : Float = 1f,
    val isPauseAudio : Boolean = false,
    val mediaPath : String? = null,
    val tickProgress : Float = 0f
) : Parcelable
package com.example.assaxiybookcompose.presenter.ui.screen.read_audio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.utils.bahaLogger
import com.example.assaxiybookcompose.utils.toReadAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadAudioViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), ReadAudioViewModel {
    override val uiState = MutableStateFlow(ReadAudioState())
    private var isPlaying = true

    init {
        toReadAudio
            .onEach {
                uiState.update { readAudioState ->
                    readAudioState.copy(bookUIData = it)
                }
                appRepository.getAudioPath(it)
                    .onEach { result ->
                        result.onSuccess { path ->
                            uiState.update { readAudioState ->

                                readAudioState.copy(mediaPath = path)
                            }
                        }.onFailure { thr ->
                            bahaLogger(thr.message ?: " Nomalum xatolik!")
                        }
                    }.launchIn(viewModelScope)
            }.launchIn(viewModelScope)

    }

    override fun onEventDispatcher(intent: ReadAudioIntent) {
        when (intent) {
            is ReadAudioIntent.OnClickBack -> {
                viewModelScope.launch {
                    appNavigator.pop()
                }
            }

            is ReadAudioIntent.AudioProgress -> {
                uiState.update {
                    it.copy(
                        progressState = intent.current,
                        maxProgress = intent.max
                    )
                }
            }

            is ReadAudioIntent.OnClickPausePlay -> {
                uiState.update {
                    it.copy(
                        isPauseAudio = isPlaying
                    )
                }
                isPlaying = !isPlaying
            }

            is ReadAudioIntent.LoadMusic -> {
                uiState.update {
                    it.copy(mediaPath = null)
                }
            }

            is ReadAudioIntent.ToProgress -> {
                uiState.update {
                    it.copy(tickProgress = intent.current)
                }
            }
        }
    }

}
package com.example.assaxiybookcompose.presenter.ui.screen.main.page.audios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
import com.example.assaxiybookcompose.presenter.ui.screen.category_by_audio.CategoryByAudioScreen
import com.example.assaxiybookcompose.presenter.ui.screen.info.InfoScreen
import com.example.assaxiybookcompose.presenter.ui.screen.read_audio.ReadAudioScreen
import com.example.assaxiybookcompose.utils.toCategoryAudio
import com.example.assaxiybookcompose.utils.toReadAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudiosViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), AudiosViewModel {
    override val audioState = MutableStateFlow(AudioState())

    init {
        audioState.update { audioState -> audioState.copy(progressState = true) }
        appRepository.getCategoryByAudioBooks()
            .onEach {
                it.onSuccess { list ->
                    audioState.update { audioState ->
                        audioState.copy(
                            progressState = false,
                            audioListData = list
                        )
                    }
                }.onFailure {
                    audioState.update { audioState -> audioState.copy(progressState = false) }
                }
            }.launchIn(viewModelScope)
    }

    override fun onEvent(intent: AudiosIntent) {
        when (intent) {
            is AudiosIntent.OnClickBook -> {
                viewModelScope.launch {
                    appNavigator.push(ReadAudioScreen())
                    toReadAudio.emit(intent.bookUIData)
                }
            }

            is AudiosIntent.OnClickCategory -> {
                viewModelScope.launch {
                    appNavigator.push(CategoryByAudioScreen())
                    toCategoryAudio.emit(intent.categoryByBooksUIData)
                }
            }
        }
    }
}
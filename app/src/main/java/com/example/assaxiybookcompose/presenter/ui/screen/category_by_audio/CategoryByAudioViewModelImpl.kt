package com.example.assaxiybookcompose.presenter.ui.screen.category_by_audio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assaxiybookcompose.domain.AppRepository
import com.example.assaxiybookcompose.navigation.AppNavigator
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
class CategoryByAudioViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appRepository: AppRepository
) : ViewModel(), CategoryByAudioViewModel {

    private var time = System.currentTimeMillis()

    override val uiState = MutableStateFlow(CategoryAudiosState())

    init {
        toCategoryAudio.onEach {
            uiState.update { categoryAudiosState ->
                categoryAudiosState.copy(
                    categoryAudios = it
                )
            }
        }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: CategoryAudiosIntent) {
        when (intent) {
            is CategoryAudiosIntent.OnClickBack -> {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    viewModelScope.launch {
                        appNavigator.pop()
                    }
                }
            }

            is CategoryAudiosIntent.OnClickAudio -> {
                if (System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    viewModelScope.launch {
                        appNavigator.push(ReadAudioScreen())
                        toReadAudio.emit(intent.bookUIData)
                    }
                }
            }
        }
    }
}
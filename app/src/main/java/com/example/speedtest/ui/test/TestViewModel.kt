package com.example.speedtest.ui.test

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedtest.data.GetSpeedNetworkRepository
import com.example.speedtest.data.RepositorySettingsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DOWN_SPEED = 1
private const val UP_SPEED = 2
private const val ALL_SPEED = 3
private const val NON_SPEED = 4

class TestViewModel @Inject constructor(
    private val getSpeedNetworkRepository: GetSpeedNetworkRepository,
    private val repositorySettingsDataStore: RepositorySettingsDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(
        TestUiState(
            downSpeed = null,
            upSpeed = null,
        )
    )
    val state = _state.asStateFlow()
    private var settingsSpeed = ALL_SPEED

    init {
        getSettings()
    }

    fun getSpeed() {
        Log.d("MyLog", "settingsSpeed $settingsSpeed")
        viewModelScope.launch(Dispatchers.IO) {
            val speed = getSpeedNetworkRepository.getSpeed()
            when (settingsSpeed) {
                ALL_SPEED -> {
                    _state.value = _state.value.copy(
                        downSpeed = speed.downSpeed.toString(),
                        upSpeed = speed.upSpeed.toString(),
                    )
                }

                DOWN_SPEED -> {
                    _state.value = _state.value.copy(
                        downSpeed = speed.downSpeed.toString(),
                        upSpeed = null,
                    )
                }

                UP_SPEED -> {
                    _state.value = _state.value.copy(
                        downSpeed = null,
                        upSpeed = speed.upSpeed.toString(),
                    )
                }

                NON_SPEED -> {
                    _state.value = _state.value.copy(
                        downSpeed = null,
                        upSpeed = null,
                    )
                }
            }
        }
    }

    fun getSettings() {
        viewModelScope.launch {
            repositorySettingsDataStore.typedSpeedFlow.collect {
                settingsSpeed = it
            }
        }
    }
}
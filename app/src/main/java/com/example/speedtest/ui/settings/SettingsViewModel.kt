package com.example.speedtest.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedtest.TypeMode
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

class SettingsViewModel @Inject constructor(
    private val repositorySettingsDataStore: RepositorySettingsDataStore,
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsUiState(
            isDarkMode = false,
            isLightMode = false,
            isDownSpeed = true,
            isUpSpeed = true,
        )
    )
    val state = _state.asStateFlow()

    init {
        getSavedSettings()
        saveSettings()
    }

    fun updateUiState(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.CheckDarkMode -> {
                _state.value = if (event.isCheck){
                    _state.value.copy(
                        isDarkMode = event.isCheck,
                        isLightMode = !event.isCheck
                    )
                } else {
                    _state.value.copy(
                        isDarkMode = event.isCheck,
                    )
                }

            }

            is SettingsEvent.CheckDownSpeed -> {
                _state.value = _state.value.copy(
                    isDownSpeed = event.isCheck
                )
            }

            is SettingsEvent.CheckLightMode -> {
                _state.value = if (event.isCheck){
                    _state.value.copy(
                        isDarkMode = !event.isCheck,
                        isLightMode = event.isCheck
                    )
                } else {
                    _state.value.copy(
                        isLightMode = event.isCheck,
                    )
                }
            }

            is SettingsEvent.CheckUpSpeed -> {
                _state.value = _state.value.copy(
                    isUpSpeed = event.isCheck
                )
            }
        }
    }

    private fun getSavedSettings() {

        viewModelScope.launch {
            val savedTypeMode = repositorySettingsDataStore.getTypeMode()

            savedTypeMode?.let { savedMode ->
                when (savedMode) {
                    TypeMode.DARK.name -> {
                        _state.value = _state.value.copy(
                            isDarkMode = true
                        )
                    }

                    TypeMode.LIGHT.name -> {
                        _state.value = _state.value.copy(
                            isLightMode = true
                        )
                    }

                    TypeMode.DEFAULT.name -> {
                        _state.value = _state.value.copy(
                            isDarkMode = false,
                            isLightMode = false
                        )
                    }
                }
            }

            val typedSpeed = repositorySettingsDataStore.getTypeSpeed()
            when (typedSpeed) {
                DOWN_SPEED -> {
                    _state.value = _state.value.copy(
                        isDownSpeed = true,
                        isUpSpeed = false
                    )
                }

                UP_SPEED -> {
                    _state.value = _state.value.copy(
                        isUpSpeed = true,
                        isDownSpeed = false
                    )
                }

                ALL_SPEED -> {
                    _state.value = _state.value.copy(
                        isDownSpeed = true,
                        isUpSpeed = true
                    )
                }

                else -> {
                    _state.value = _state.value.copy(
                        isDownSpeed = false,
                        isUpSpeed = false
                    )
                }
            }
        }
    }

    private fun saveSettings() {

        viewModelScope.launch(Dispatchers.IO) {
            state.collect { state ->

                val typeMode = if (state.isDarkMode) {
                    TypeMode.DARK.name
                } else if (state.isLightMode) {
                    TypeMode.LIGHT.name
                } else TypeMode.DEFAULT.name

                val typeSpeed = if (state.isDownSpeed && state.isUpSpeed) {
                    ALL_SPEED
                } else if (!state.isDownSpeed && !state.isUpSpeed) {
                    NON_SPEED
                } else if (state.isDownSpeed) {
                    DOWN_SPEED
                } else UP_SPEED

                repositorySettingsDataStore.saveTypeMode(typeMode)
                repositorySettingsDataStore.saveTypeSpeed(typeSpeed)
            }
        }
    }
}
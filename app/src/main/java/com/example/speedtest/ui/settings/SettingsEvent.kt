package com.example.speedtest.ui.settings

//действия пользователя которые могут происходить
sealed class SettingsEvent {
    data class CheckDarkMode(val isCheck: Boolean): SettingsEvent()
    data class CheckLightMode(val isCheck: Boolean): SettingsEvent()
    data class CheckDownSpeed(val isCheck: Boolean): SettingsEvent()
    data class CheckUpSpeed(val isCheck: Boolean): SettingsEvent()
}
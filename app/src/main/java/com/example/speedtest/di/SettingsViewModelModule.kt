package com.example.speedtest.di

import androidx.lifecycle.ViewModel
import com.example.speedtest.ui.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

//модуль в котором говорим Dagger как создать SettingsViewModel
@Module
interface SettingsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindMainViewModel(viewModel: SettingsViewModel): ViewModel
}
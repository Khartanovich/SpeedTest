package com.example.speedtest.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

//модуль в котором говорим Dagger как создать ViewModelFactory
@Module
interface ViewModelFactoryModule {

    @Singleton
    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
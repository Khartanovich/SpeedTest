package com.example.speedtest.di

import androidx.lifecycle.ViewModel
import com.example.speedtest.ui.test.TestViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

//модуль в котором говорим Dagger как создать TestViewModel
@Module
interface TestViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    fun bindMainViewModel(viewModel: TestViewModel): ViewModel
}
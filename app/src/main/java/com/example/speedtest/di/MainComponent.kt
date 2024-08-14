package com.example.speedtest.di

import android.app.Application
import android.content.Context
import com.example.speedtest.MainActivity
import com.example.speedtest.ui.settings.SettingsFragment
import com.example.speedtest.ui.test.TestFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

//компонент генерируемый Dagger который будет создавать все необходимые зависимости
@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        SettingsViewModelModule::class,
        ViewModelFactoryModule::class,
        TestViewModelModule::class
    ]
)
interface MainComponent {

    fun inject(application: Application)

    companion object {
        fun init(app: Application): MainComponent {
            return DaggerMainComponent.factory()
                .create(app)


        }
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainComponent
    }

    fun inject(fragment: SettingsFragment)
    fun inject(fragment: TestFragment)
    fun inject(activity: MainActivity)
}
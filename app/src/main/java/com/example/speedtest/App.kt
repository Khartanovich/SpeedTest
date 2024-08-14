package com.example.speedtest

import android.app.Application
import com.example.speedtest.di.MainComponent

class App: Application() {

    lateinit var appComponent: MainComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = MainComponent.init(this)
    }
}
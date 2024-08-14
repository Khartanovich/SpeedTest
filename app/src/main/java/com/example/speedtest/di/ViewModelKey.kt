package com.example.speedtest.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

//анотация которая поможет положить в Map определенную viewModel и получить ее потом
@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
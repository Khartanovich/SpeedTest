package com.example.speedtest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//репозиторий для сохранения настроек
class RepositorySettingsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object{
        val TYPE_MODE = stringPreferencesKey("TYPE_MODE")
        val SPEED = intPreferencesKey("SPEED")
        const val ALL_SPEED = 3
    }

    suspend fun saveTypeMode(typeMode: String){
        dataStore.edit { pref ->
            pref[TYPE_MODE] = typeMode
        }
    }

    suspend fun getTypeMode(): String?{
        return dataStore.data.map { pref ->
            pref[TYPE_MODE]
        }.first()
    }

    suspend fun saveTypeSpeed(typeSpeed: Int){
        dataStore.edit { pref ->
            pref[SPEED] = typeSpeed
        }
    }

    suspend fun getTypeSpeed(): Int{
        return dataStore.data.map { pref ->
            pref[SPEED] ?: ALL_SPEED
        }.first()
    }

    val typedSpeedFlow = dataStore.data.map { pref ->
        pref[SPEED] ?: ALL_SPEED
    }
}
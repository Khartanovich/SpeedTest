package com.example.speedtest

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.speedtest.data.RepositorySettingsDataStore
import com.example.speedtest.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ChangeTheme {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repositorySettingsDataStore: RepositorySettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        (this.application as App).appComponent.inject(this)
        setSavedTheme()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHost.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_test, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun changeTheme(typeMode: TypeMode) {
        when (typeMode) {
            TypeMode.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            TypeMode.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            TypeMode.DEFAULT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    private fun setSavedTheme() {
        lifecycleScope.launch {
            val savedTypeMode = repositorySettingsDataStore.getTypeMode()
            savedTypeMode?.let { savedMode ->
                when (savedMode) {
                    TypeMode.DARK.name -> {
                        changeTheme(TypeMode.DARK)
                    }

                    TypeMode.LIGHT.name -> {
                        changeTheme(TypeMode.LIGHT)
                    }

                    TypeMode.DEFAULT.name -> {
                        changeTheme(TypeMode.DEFAULT)
                    }
                }
            }
        }
    }
}
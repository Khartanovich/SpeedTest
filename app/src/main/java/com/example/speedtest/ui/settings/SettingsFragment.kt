package com.example.speedtest.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.speedtest.App
import com.example.speedtest.ChangeTheme
import com.example.speedtest.TypeMode
import com.example.speedtest.databinding.FragmentSettingsBinding
import com.example.speedtest.di.ViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCheckBoxesListeners()
        updateState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCheckBoxesListeners() {
        with(binding) {
            checkDarkMode.setOnCheckedChangeListener { _, b ->
                viewModel.updateUiState(SettingsEvent.CheckDarkMode(b))
            }
            checkLightMode.setOnCheckedChangeListener { _, b ->
                viewModel.updateUiState(SettingsEvent.CheckLightMode(b))
            }
            checkDownSpeed.setOnCheckedChangeListener { _, b ->
                viewModel.updateUiState(SettingsEvent.CheckDownSpeed(b))
            }
            checkUpSpeed.setOnCheckedChangeListener { _, b ->
                viewModel.updateUiState(SettingsEvent.CheckUpSpeed(b))
            }

        }
    }

    private fun updateState() {
        viewModel.state.onEach { state ->
            if (state.isLightMode) {
                (requireActivity() as ChangeTheme).changeTheme(TypeMode.LIGHT)
            } else if (state.isDarkMode) {
                (requireActivity() as ChangeTheme).changeTheme(TypeMode.DARK)
            } else {
                (requireActivity() as ChangeTheme).changeTheme(TypeMode.DEFAULT)
            }
            with(binding) {
                checkDarkMode.isChecked = state.isDarkMode
                checkLightMode.isChecked = state.isLightMode
                checkDownSpeed.isChecked = state.isDownSpeed
                checkUpSpeed.isChecked = state.isUpSpeed
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
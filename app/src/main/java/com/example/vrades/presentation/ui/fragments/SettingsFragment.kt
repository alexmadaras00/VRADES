package com.example.vrades.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentSettingsBinding
import com.example.vrades.domain.model.Settings
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.utils.Constants.ERROR_REF
import com.example.vrades.presentation.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel by viewModels<SettingsViewModel>()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var defaultTutorial = false
    private var defaultSuggestions = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.apply {
            executePendingBindings()
        }
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractPreferences()
        binding.apply {
            val buttonBack = btnBack
            val buttonLogOut = btnLogOut
            val switchTutorial = swTutorial
            val switchDisplaySuggestions = swDisplaySuggestions

            buttonBack.setOnClickListener {
                onNavigateToHome()
            }
            buttonLogOut.setOnClickListener {
                logOut()
            }
            switchTutorial.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    // Update ViewModel's LiveData

                    viewModel?.tutorialEnabled?.value = isChecked
                    viewModel?.savePreferences()

                }
            }
            switchDisplaySuggestions.apply {
                setOnCheckedChangeListener { _, isChecked ->
                    Log.d("PREF FRAGMENT","PREF: ${viewModel?.displaySuggestion?.value}")
                    Log.d("VIEW MODEL:","VM:${viewModel}")
                    // Update ViewModel's LiveData
                    viewModel?.displaySuggestion?.value = isChecked
                    viewModel?.savePreferences()

                    Log.d("PREF FRAGMENT","PREF: ${viewModel?.displaySuggestion?.value}")
                }
            }
        }
    }

    private fun logOut() {
        viewModel.logOut().observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    onNavigateToLogin()
                }

                is Response.Error -> {
                    println(ERROR_REF)
                }

                else -> {

                }
            }
        }
    }

    private fun onNavigateToHome() {
        findNavController().navigate(SettingsFragmentDirections.actionNavSettingsToNavHome())
    }

    private fun onNavigateToLogin() {
        findNavController().navigate(SettingsFragmentDirections.actionNavSettingsToNavLogin())
    }

    private fun extractPreferences() {
        viewModel.extractPreferences()
        viewModel.settings.observe(viewLifecycleOwner) { settings ->
            binding.apply {
                swTutorial.isChecked = settings.tutorialEnabled
                swDisplaySuggestions.isChecked = settings.displaySuggestions
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

}
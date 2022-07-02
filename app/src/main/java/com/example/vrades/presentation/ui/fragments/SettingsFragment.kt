package com.example.vrades.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentSettingsBinding
import com.example.vrades.domain.model.Preferences
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.utils.Constants.ERROR_REF
import com.example.vrades.presentation.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel by viewModels<SettingsViewModel>()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var defaultTutorial = "OFF"
    private var defaultSuggestions = "OFF"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
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
                    defaultTutorial = if (isChecked) {
                        textOn.toString()
                    } else {
                        textOff.toString()
                    }
                    viewModel?.savePreferences(Preferences(tutorialEnabled = defaultTutorial))
                    println("TUTORIAL: $defaultTutorial, ${Preferences(tutorialEnabled = defaultTutorial)}")
                }

            }
            switchDisplaySuggestions.apply{
                setOnCheckedChangeListener { _, isChecked ->
                    defaultSuggestions = if (isChecked) {
                        textOn.toString()
                    } else {
                        textOff.toString()
                    }
                    viewModel?.savePreferences(Preferences(displaySuggestions = defaultSuggestions))
                    println("SUGG: ${Preferences(displaySuggestions = defaultSuggestions)}")
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

        viewModel.extractPreferences().observe(viewLifecycleOwner) {
            binding.apply {
                val switchTutorial = swTutorial
                val switchDisplaySuggestions = swDisplaySuggestions

                if (it.tutorialEnabled != defaultTutorial) {
                    switchTutorial.apply {
                        isChecked = true
                        requestFocus()
                    }
                }
                if (it.displaySuggestions != defaultSuggestions) {
                    switchDisplaySuggestions.apply {
                        isChecked = true
                        requestFocus()
                    }
                }
            }
            println("TUTORIAL PREFERENCE: ${it.tutorialEnabled},${it.displaySuggestions}")

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
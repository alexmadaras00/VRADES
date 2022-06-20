package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentSettingsBinding
import com.example.vrades.model.Response
import com.example.vrades.utils.Constants.ERROR_REF
import com.example.vrades.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        _binding = FragmentSettingsBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            val buttonBack = btnBack
            val buttonLogOut = btnLogOut
            buttonBack.setOnClickListener {
                onNavigateToHome()
            }
            buttonLogOut.setOnClickListener {
                logOut()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    companion object {
        fun newInstance() = SettingsFragment()
    }



}
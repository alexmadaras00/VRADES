package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.FragmentAboutBinding
import com.example.vrades.databinding.FragmentAboutBindingImpl
import com.example.vrades.databinding.FragmentSettingsBinding
import com.example.vrades.databinding.FragmentSettingsBindingImpl
import com.example.vrades.viewmodels.SettingsViewModel

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }
    private lateinit var viewModel: SettingsViewModel
    private val _binding: FragmentSettingsBinding? = null
    var binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBindingImpl.inflate(inflater)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }



}
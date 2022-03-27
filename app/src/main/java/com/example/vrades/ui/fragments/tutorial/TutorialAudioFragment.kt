package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.FragmentSettingsBinding
import com.example.vrades.databinding.FragmentSettingsBindingImpl
import com.example.vrades.databinding.FragmentTutorialAudioBinding
import com.example.vrades.viewmodels.SettingsViewModel
import com.example.vrades.viewmodels.TutorialViewModel


class TutorialAudioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewModel: TutorialViewModel
    private var _binding: FragmentTutorialAudioBinding? = null
    var binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialAudioBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
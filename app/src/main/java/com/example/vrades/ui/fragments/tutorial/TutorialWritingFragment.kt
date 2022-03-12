package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTutorialAudioBinding
import com.example.vrades.databinding.FragmentTutorialWritingBinding
import com.example.vrades.viewmodels.TutorialViewModel


class TutorialWritingFragment : Fragment() {
    private lateinit var viewModel: TutorialViewModel
    private val _binding: FragmentTutorialWritingBinding? = null
    var binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialWritingBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

}
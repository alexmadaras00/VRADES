package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTutorialAudioBinding
import com.example.vrades.databinding.FragmentTutorialFinishedBinding
import com.example.vrades.viewmodels.TutorialViewModel


class TutorialFinishedFragment : Fragment() {

    private lateinit var viewModel: TutorialViewModel
    private var _binding: FragmentTutorialFinishedBinding? = null
    var binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialFinishedBinding.inflate(inflater)
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
package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTutorialAudioBinding
import com.example.vrades.databinding.FragmentTutorialFaceDetectionBinding
import com.example.vrades.databinding.FragmentTutorialHomeBinding
import com.example.vrades.ui.fragments.VradesBaseFragment
import com.example.vrades.viewmodels.TutorialViewModel


class TutorialFaceDetectionFragment : VradesBaseFragment() {
    // TODO: Rename and change types of parameters
    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialFaceDetectionBinding? = null
    val binding get() = _binding!!
    override fun connectViewModelEvents() {

    }

    override fun disconnectViewModelEvents() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialFaceDetectionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
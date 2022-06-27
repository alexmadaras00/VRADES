package com.example.vrades.presentation.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.vrades.databinding.FragmentTutorialHomeBinding
import com.example.vrades.presentation.enums.TutorialState
import com.example.vrades.presentation.ui.fragments.VradesBaseFragment
import com.example.vrades.presentation.viewmodels.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialHomeFragment : VradesBaseFragment() {


    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialHomeBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTutorialHomeBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.stateTutorial = TutorialState.START_PAGE.position
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.apply { }

    }

    override fun connectViewModelEvents() {
        TODO("Not yet implemented")
    }

    override fun disconnectViewModelEvents() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
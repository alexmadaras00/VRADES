package com.example.vrades.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentHomeBinding
import com.example.vrades.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        checkUserState()
        binding.apply {
            val aboutButton = btnAbout
            val testButton = btnDetect
            val settingsButton = btnSettings
            val tutorialButton = btnTutorial
            val profileButton = btnProfile
            aboutButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavAbout())
            }
            testButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavFace())
            }
            settingsButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavSettings())
            }
            tutorialButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavTutorial())
            }
            profileButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavProfile())
            }
        }
    }

    private fun checkUserState() {
        if (!viewModel.isUserAuthenticated) {
            onNavigateToLogin()
        }
    }

    private fun onNavigateToLogin() {
        val actionLogin = HomeFragmentDirections.actionNavHomeToNavLogin()
        findNavController().navigate(actionLogin)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
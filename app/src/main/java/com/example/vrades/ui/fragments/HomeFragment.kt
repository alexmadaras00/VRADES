package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vrades.R
import com.example.vrades.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
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
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavTest())
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
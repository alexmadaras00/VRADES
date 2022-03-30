package com.example.vrades.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentAudioTestBinding
import com.example.vrades.enums.TestState
import com.example.vrades.viewmodels.LoginViewModel
import com.example.vrades.viewmodels.TestViewModel


class AudioTestFragment : Fragment() {


    private var _binding: FragmentAudioTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TestViewModel

    companion object {
        fun newInstance() = FaceDetectionFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TestViewModel::class.java]
        _binding = FragmentAudioTestBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val state = viewModel.getState()
        binding.apply {
            if (state == TestState.AUDIO_DETECTION_COMPLETED) {
                val buttonNext = btnNext
                val buttonRestart = btnRestart
                buttonNext.setOnClickListener {
                    findNavController().navigate(AudioTestFragmentDirections.actionAudioTestFragmentToWritingTestFragment())
                }
                buttonRestart.setOnClickListener {
                    findNavController().navigate(AudioTestFragmentDirections.actionAudioTestFragmentSelf())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
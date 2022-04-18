package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentAudioTestBinding
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
        binding.viewModelTest = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val stateAudio = viewModel.getCurrentAudioState()
        val navController = findNavController()
        binding.apply {
            val buttonRecord = fbtnVoiceRecord
            buttonRecord.setOnClickListener {
                viewModel.setAudioStateCount(1)
//            if (stateAudio == AudioState.DONE_RECORDING) {
                val buttonNext = btnProceed
                buttonRecord.visibility = View.INVISIBLE
                buttonNext.visibility = View.VISIBLE
                buttonNext.setOnClickListener {
                    navController.navigate(AudioTestFragmentDirections.actionAudioTestFragmentToWritingTestFragment())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentFaceDetectionBinding
import com.example.vrades.enums.TestState
import com.example.vrades.viewmodels.TestViewModel


class FaceDetectionFragment : Fragment() {

    private var _binding: FragmentFaceDetectionBinding? = null
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
        _binding = FragmentFaceDetectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val state = viewModel.getState()

        binding.apply {
            if (state == TestState.FACE_DETECTION_COMPLETED) {
                val buttonNext = btnNextFace
                buttonNext.setOnClickListener {
                    findNavController().navigate(FaceDetectionFragmentDirections.actionFaceDetectionFragmentToAudioTestFragment())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
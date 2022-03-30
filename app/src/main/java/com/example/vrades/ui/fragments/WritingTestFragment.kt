package com.example.vrades.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentWritingTestBinding
import com.example.vrades.enums.TestState
import com.example.vrades.viewmodels.TestViewModel


class WritingTestFragment : Fragment() {

    private var _binding: FragmentWritingTestBinding? = null
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
        _binding = FragmentWritingTestBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val state = viewModel.getState()
        binding.apply {
            if (state == TestState.WRITING_DETECTION_COMPLETED) {
                val buttonProceed = btnProceed
                val testFragment = requireParentFragment()
                buttonProceed.setOnClickListener {
                    testFragment.findNavController().navigate(TestFragmentDirections.actionNavTestToNavDetails())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
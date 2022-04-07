package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vrades.databinding.FragmentTutorialFinishedBinding
import com.example.vrades.viewmodels.TutorialViewModel


class TutorialFinishedFragment : Fragment() {

    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialFinishedBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTutorialFinishedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            val buttonNext = btnNextTutorial
            if (viewModel!!.getCurrentStateData() == 6) {
                buttonNext.setOnClickListener {
                    viewModelStore.clear()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
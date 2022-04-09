package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vrades.R
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
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            val buttonNext = btnNextTutorial
            val textViewCompletedTutorial = tvCompletedTutorial
            val textViewDone = tvDone
            val viewModel = viewModel!!
            val currentStateCount = viewModel.getCurrentStateData()
            val currentState = viewModel.getCurrentState()
            println(currentState)
            if (currentStateCount == 5) {
                textViewCompletedTutorial.text = getString(R.string.good_to_go)
                textViewDone.visibility = View.VISIBLE
                buttonNext.setOnClickListener {
                    viewModel.onNavigateToHomeScreenClicked()
                    viewModelStore.clear()
                }
            } else {
                buttonNext.setOnClickListener {
                    viewModel.onNextPageClicked()
                }
                textViewDone.visibility = View.INVISIBLE
                textViewCompletedTutorial.text = getString(R.string.tutorial_finished)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

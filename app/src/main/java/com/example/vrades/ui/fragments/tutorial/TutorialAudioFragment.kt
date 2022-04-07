package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vrades.databinding.FragmentTutorialAudioBinding
import com.example.vrades.viewmodels.TutorialViewModel


class TutorialAudioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialAudioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialAudioBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            val buttonRecordAudio = fbtnVoiceRecord
            val buttonNext = btnNextTutorialAudio
            val textAudioCompleted = tvAudioCompleted
            val textViewTap = tvTapToStart
            val imageViewArrow = ivArrowTutorialAudio
            val textViewPressAudio = tvPressAudio
            buttonRecordAudio.setOnClickListener {
                textAudioCompleted.visibility = View.VISIBLE
                buttonNext.isEnabled = true
                buttonRecordAudio.visibility = View.INVISIBLE
                textViewTap.visibility = View.INVISIBLE
                imageViewArrow.visibility = View.INVISIBLE
                textViewPressAudio.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
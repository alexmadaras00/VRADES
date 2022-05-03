package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTutorialAudioBinding
import com.example.vrades.viewmodels.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            startAnimate(imageViewArrow)

            buttonRecordAudio.setOnClickListener {
                textAudioCompleted.visibility = View.VISIBLE
                buttonNext.isEnabled = true
                buttonRecordAudio.visibility = View.INVISIBLE
                textViewTap.visibility = View.INVISIBLE
                imageViewArrow.visibility = View.INVISIBLE
                stopAnimate(imageViewArrow)
                textViewPressAudio.visibility = View.INVISIBLE
            }
        }
    }

    private fun startAnimate(imageViewArrow: ImageView) {
        val animUpDown = AnimationUtils.loadAnimation(
            context,
            R.anim.arrow_anim_horizontal
        );
        imageViewArrow.startAnimation(animUpDown);
    }
    private fun stopAnimate(imageViewArrow: ImageView) {
        imageViewArrow.animation.cancel()
        imageViewArrow.clearAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
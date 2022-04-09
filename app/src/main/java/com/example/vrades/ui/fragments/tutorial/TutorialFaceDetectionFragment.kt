package com.example.vrades.ui.fragments.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTutorialFaceDetectionBinding
import com.example.vrades.ui.fragments.VradesBaseFragment
import com.example.vrades.viewmodels.TutorialViewModel


class TutorialFaceDetectionFragment : VradesBaseFragment() {
    // TODO: Rename and change types of parameters
    private val viewModel: TutorialViewModel by activityViewModels()
    private var _binding: FragmentTutorialFaceDetectionBinding? = null
    val binding get() = _binding!!
    override fun connectViewModelEvents() {

    }

    override fun disconnectViewModelEvents() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialFaceDetectionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val imageViewArrow = binding.ivArrowTutorial
        startAnimate(imageViewArrow)
    }

    private fun startAnimate(imageViewArrow: ImageView) {
        val animUpDown = AnimationUtils.loadAnimation(
            context,
            R.anim.arrow_animation
        );
        imageViewArrow.startAnimation(animUpDown);
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
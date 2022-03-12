package com.example.vrades.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.FragmentAudioTestBinding
import com.example.vrades.databinding.FragmentFaceDetectionBinding
import com.example.vrades.databinding.FragmentHomeBinding
import com.example.vrades.viewmodels.TestViewModel


class AudioTestFragment : Fragment() {


    private val _bindings: FragmentAudioTestBinding? = null
    private var bindings = _bindings!!
    private lateinit var viewModel: TestViewModel

    companion object {
        fun newInstance() = FaceDetectionFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentAudioTestBinding.inflate(inflater)
        bindings.lifecycleOwner = this
        bindings.executePendingBindings()
        return bindings.root
    }


}
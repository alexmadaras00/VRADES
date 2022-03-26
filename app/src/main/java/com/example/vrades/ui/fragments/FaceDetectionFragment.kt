package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vrades.databinding.FragmentFaceDetectionBinding
import com.example.vrades.viewmodels.FaceDetectionViewModel


class FaceDetectionFragment : Fragment() {

    private val _bindings: FragmentFaceDetectionBinding? = null
    private var bindings = _bindings!!
    private lateinit var viewModel: FaceDetectionViewModel

    companion object {
        fun newInstance() = FaceDetectionFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentFaceDetectionBinding.inflate(inflater)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel
        bindings.executePendingBindings()
        return bindings.root
    }


}
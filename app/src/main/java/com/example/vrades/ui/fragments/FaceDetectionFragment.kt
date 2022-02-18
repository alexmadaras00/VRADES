package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.viewmodels.FaceDetectionViewModel

class FaceDetectionFragment : Fragment() {

    companion object {
        fun newInstance() = FaceDetectionFragment()
    }

    private lateinit var viewModel: FaceDetectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.face_detection_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FaceDetectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
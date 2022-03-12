package com.example.vrades.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.FragmentFaceDetectionBinding
import com.example.vrades.databinding.FragmentHomeBinding
import com.example.vrades.databinding.LoginFragmentBinding
import com.example.vrades.viewmodels.TestViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val _bindings: FragmentHomeBinding? = null
    private var bindings = _bindings!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindings = FragmentHomeBinding.inflate(inflater)
        bindings.lifecycleOwner = this

        bindings.executePendingBindings()
        return bindings.root
    }

}
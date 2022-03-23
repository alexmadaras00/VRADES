package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.FragmentDetailsBinding
import com.example.vrades.databinding.FragmentSolutionsBinding
import com.example.vrades.viewmodels.SolutionsViewModel

class SolutionsFragment : Fragment() {


    private lateinit var viewModel: SolutionsViewModel
    private val _binding: FragmentSolutionsBinding? = null
    var binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSolutionsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SolutionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
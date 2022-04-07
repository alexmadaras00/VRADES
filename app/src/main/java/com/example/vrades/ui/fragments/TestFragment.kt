package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.vrades.R
import com.example.vrades.databinding.FragmentTestBinding
import com.example.vrades.viewmodels.TestViewModel

class TestFragment : Fragment() {

    companion object {
        fun newInstance() = TestFragment()
    }

    private lateinit var viewModel: TestViewModel
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TestViewModel::class.java]
        _binding = FragmentTestBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.apply {
            val bottomNavigationView = bnNavigationTest
            val navController = Navigation.findNavController(requireActivity(),R.id.nav_host_tutorial)
            bottomNavigationView.setupWithNavController(navController)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
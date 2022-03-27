package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.databinding.FragmentLoginBinding
import com.example.vrades.viewmodels.LoginViewModel
import com.example.vrades.viewmodels.SolutionsViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        val loginButton = binding.btnLogIn
        loginButton.setOnClickListener {
            find
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
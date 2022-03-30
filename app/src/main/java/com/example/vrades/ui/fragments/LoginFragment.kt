package com.example.vrades.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentLoginBinding
import com.example.vrades.viewmodels.LoginViewModel

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
        val forgotPasswordTextView = binding.tvForgotPassword
        val noAccountTextView = binding.tvNoAccount
        val actionHome = LoginFragmentDirections.actionNavLoginToNavHome()
        val actionForgot = LoginFragmentDirections.actionNavLoginToNavForgot()
        val actionRegister = LoginFragmentDirections.actionNavLoginToNavRegister()
        loginButton.setOnClickListener {
            findNavController().navigate(actionHome)
        }
        forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(actionForgot)
        }
        noAccountTextView.setOnClickListener {
            findNavController().navigate(actionRegister)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
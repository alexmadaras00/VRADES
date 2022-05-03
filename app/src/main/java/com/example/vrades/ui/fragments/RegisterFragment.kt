package com.example.vrades.ui.fragments

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentRegisterBinding
import com.example.vrades.model.Response
import com.example.vrades.utils.Constants
import com.example.vrades.utils.LoginValidator
import com.example.vrades.utils.UIUtils.toast
import com.example.vrades.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    VradesBindingFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        binding.apply {
            val buttonRegister = btnRegister
            val textViewAlreadyAccount = tvAlreadyAccount
            buttonRegister.setOnClickListener {
                validateAndRegister()
            }
            textViewAlreadyAccount.setOnClickListener {
                onNavigateToLogin()
            }
        }
    }

    private fun validateAndRegister() {
        binding.apply {
            val fullName = etFullname
            val email = etEmail2
            val password = etPassword2
            val confirmPassword = etConfirmPassword
            val validator =
                LoginValidator(requireContext(), email, password, confirmPassword, fullName)
            val isValidEmail = validator.validateEmail()
            val isValidPassword = validator.validatePassword()
            val isValidConfirmPassword = validator.validateRepeatPassword()
            val isValidFullName = validator.validateName()
            if (isValidEmail && isValidFullName && isValidPassword && isValidConfirmPassword) {
                registerWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString(),
                    fullName.text.toString()
                )
            }
        }
    }

    private fun registerWithEmailAndPassword(email: String, password: String, fullName: String) {
        viewModel.firebaseRegisterWithEmail(email, password)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Response.Success -> {
                        createUserInRealtime(email, password, fullName)
                        println("Success")
                    }
                    is Response.Error -> {
                        println(Constants.ERROR_REF)
                    }
                    else -> {}
                }
            }
    }

    private fun createUserInRealtime(email: String, password: String, fullName: String) {
        viewModel.firebaseCreateRealtimeUser(email, password).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    toast(requireActivity().applicationContext, "Welcome, $fullName !")
                    onNavigateToHome()
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {}
            }
        }
    }

    private fun onNavigateToHome() {
        val actionHome = RegisterFragmentDirections.actionNavRegisterToNavHome()
        findNavController().navigate(actionHome)
    }

    private fun onNavigateToLogin() {
        findNavController().navigate(RegisterFragmentDirections.actionNavRegisterToNavLogin())
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }


}
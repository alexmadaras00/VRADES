package com.example.vrades.presentation.ui.fragments

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentLoginBinding
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.utils.Constants.ERROR_REF
import com.example.vrades.presentation.utils.LoginValidator
import com.example.vrades.presentation.utils.UIUtils
import com.example.vrades.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : VradesBindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        binding.apply {
            val loginButton = btnLogIn
            val forgotPasswordTextView = tvForgotPassword
            val noAccountTextView = tvNoAccount
            val editTextEmail = etEmail
            val editTextPassword = etPassword
            val editTexts = arrayOf(editTextEmail, editTextPassword)
            loginButton.setOnClickListener {
                validateAndLogin()
            }
            forgotPasswordTextView.setOnClickListener {
                onNavigateToForgot()
            }
            noAccountTextView.setOnClickListener {
                onNavigateToRegister()
            }
            editTexts.forEach {
                it.setOnEditorActionListener(TextView.OnEditorActionListener { _, p1, _ ->
                    if (p1 == EditorInfo.IME_ACTION_DONE || p1 == EditorInfo.IME_ACTION_SEND || p1 == EditorInfo.IME_ACTION_NEXT) {
                        UIUtils.dismissKeyboard(requireActivity())
                    }
                    return@OnEditorActionListener false
                })
            }
        }
    }

    private fun validateAndLogin() {
        binding.apply {
            val email = etEmail
            val password = etPassword
            val validator = LoginValidator(requireContext(), email, password)
            val isValidEmail = validator.validateEmail()
            val isValidPassword = validator.validatePassword()
            if (isValidEmail && isValidPassword) {
                loginWithEmailAndPassword(email.text.toString(), password.text.toString())
            }
        }
    }

    private fun loginWithEmailAndPassword(email: String, password: String) {

        viewModel.firebaseSignInWithEmail(email, password)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Response.Success -> {
                        onNavigateToHome()
                        println("Success")
                    }
                    is Response.Error -> {
                        println(ERROR_REF)
                    }
                    else -> {}
                }
            }

    }

    private fun onNavigateToHome() {
        val actionHome = LoginFragmentDirections.actionNavLoginToNavHome()
        findNavController().navigate(actionHome)
    }

    private fun onNavigateToForgot() {
        val actionForgot = LoginFragmentDirections.actionNavLoginToNavForgot()
        findNavController().navigate(actionForgot)
    }

    private fun onNavigateToRegister() {
        val actionRegister = LoginFragmentDirections.actionNavLoginToNavRegister()
        findNavController().navigate(actionRegister)

    }

    companion object {
        fun newInstance() = LoginFragment()
    }


}
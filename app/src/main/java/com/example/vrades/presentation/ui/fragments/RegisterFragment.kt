package com.example.vrades.ui.fragments

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentRegisterBinding
import com.example.vrades.model.Response
import com.example.vrades.utils.Constants
import com.example.vrades.utils.LoginValidator
import com.example.vrades.utils.UIUtils
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
            val editTexts = arrayOf(fullName, email, password, confirmPassword)
            editTexts.forEach {
                it.setOnEditorActionListener(TextView.OnEditorActionListener { _, p1, _ ->
                    if (p1 == EditorInfo.IME_ACTION_DONE || p1 == EditorInfo.IME_ACTION_SEND || p1 == EditorInfo.IME_ACTION_NEXT) {
                        UIUtils.dismissKeyboard(requireActivity())
                    }
                    return@OnEditorActionListener false
                })
            }
            if (isValidEmail && isValidFullName && isValidPassword && isValidConfirmPassword) {
                checkEmailInAuth(
                    email.text.toString(),
                    password.text.toString(),
                    fullName.text.toString()
                )
            }
        }
    }

    private fun checkEmailInAuth(email: String, password: String, fullName: String) {
        viewModel.isAccountInAuth(email).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    if (it.data == 0)
                        registerWithEmailAndPassword(email, password, fullName)
                    else toast(
                        requireContext(),
                        "User already exists. Please try to enter a new one!"
                    )
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {}
            }

        }
    }

    private fun registerWithEmailAndPassword(email: String, password: String, fullName: String) {
        viewModel.firebaseRegisterWithEmail(email, password)
            .observe(viewLifecycleOwner) {
                when (it) {
                    is Response.Success -> {
                        createUserInRealtime(fullName)
                        println("Success")
                    }
                    is Response.Error -> {
                        println(Constants.ERROR_REF)
                    }
                    else -> {}
                }
            }
    }

    private fun createUserInRealtime(fullName: String) {
        viewModel.firebaseCreateRealtimeUser(fullName).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    toast(requireActivity().applicationContext, "Welcome, $fullName !")
                    onNavigateToTutorial()
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {}
            }
        }
    }



    private fun onNavigateToTutorial() {
        val actionTutorial = RegisterFragmentDirections.actionNavRegisterToNavTutorial()
        findNavController().navigate(actionTutorial)
    }

    private fun onNavigateToLogin() {
        val actionLogin = RegisterFragmentDirections.actionNavRegisterToNavLogin()
        findNavController().navigate(actionLogin)
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }


}
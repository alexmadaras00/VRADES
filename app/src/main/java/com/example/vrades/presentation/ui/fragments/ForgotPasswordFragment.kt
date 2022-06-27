package com.example.vrades.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vrades.databinding.FragmentForgotPasswordBinding
import com.example.vrades.domain.model.Response
import com.example.vrades.presentation.utils.Constants
import com.example.vrades.presentation.utils.LoginValidator
import com.example.vrades.presentation.utils.UIUtils
import com.example.vrades.presentation.utils.UIUtils.toast
import com.example.vrades.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            val buttonResetPassword = btnSubmitForgotPassword
            buttonResetPassword.setOnClickListener {
                checkEmailAddress()
            }
        }
    }

    private fun checkEmailAddress() {
        binding.apply {
            val emailReceivePassword = etEmailReceivePassword
            val validator = LoginValidator(requireContext(), emailReceivePassword)
            val email = emailReceivePassword.text.toString().trim()
            val isValid = validator.validateEmailResend()
            emailReceivePassword.setOnEditorActionListener(TextView.OnEditorActionListener { _, p1, _ ->
                if (p1 == EditorInfo.IME_ACTION_DONE || p1 == EditorInfo.IME_ACTION_SEND || p1 == EditorInfo.IME_ACTION_NEXT) {
                    UIUtils.dismissKeyboard(requireActivity())
                }
                return@OnEditorActionListener false
            })
            if (isValid) {
                checkEmailInAuth(email)
            }
        }
    }

    private fun checkEmailInAuth(email: String) {
        viewModel.isAccountInAuth(email).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    if (it.data == 1)
                        resetPassword(email)
                    else toastInvalid()
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {}
            }

        }
    }

    private fun resetPassword(email: String) {
        viewModel.resetPassword(email).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    onNavigateToLogin()
                    toast(requireContext(), "An email has been sent to the e-mail address.")
                }
                is Response.Error -> {
                    println(Constants.ERROR_REF)
                }
                else -> {}
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun toastInvalid() {
        toast(requireContext(), "This e-mail is not valid! Please try again.")
    }

    private fun onNavigateToLogin() {
        findNavController().navigate(ForgotPasswordFragmentDirections.actionNavForgotToNavLogin())
    }
}
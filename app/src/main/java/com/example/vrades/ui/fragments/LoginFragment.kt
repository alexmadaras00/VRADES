package com.example.vrades.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vrades.R
import com.example.vrades.databinding.LoginFragmentBinding
import com.example.vrades.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val _bindings: LoginFragmentBinding? = null
    private var bindings = _bindings!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[LoginViewModel::class.java]
        bindings = LoginFragmentBinding.inflate(inflater)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel
        bindings.executePendingBindings()
        return bindings.root

    }

    override fun onStart() {
        super.onStart()
    }


}
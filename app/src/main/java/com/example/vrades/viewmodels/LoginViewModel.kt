package com.example.vrades.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.vrades.ui.fragments.LoginFragment


class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun navigateTo(view: View, destination: Int) {
        val action = LoginFragment::class.java
        Navigation.findNavController(view).navigate(destination)
    }
}
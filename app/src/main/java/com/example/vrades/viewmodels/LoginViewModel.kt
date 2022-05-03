package com.example.vrades.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.navigation.Navigation
import com.example.vrades.firebase.domain.use_cases.AuthUseCases
import com.example.vrades.ui.fragments.LoginFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCasesAuth: AuthUseCases
) : ViewModel() {
    val authState = useCasesAuth.getAuthState()

    fun isAccountInAuth(email: String) = liveData(Dispatchers.IO) {
        useCasesAuth.isAccountInAuth(email).collect { result -> emit(result) }
    }

    fun navigateTo(view: View, destination: Int) {
        val action = LoginFragment::class.java
        Navigation.findNavController(view).navigate(destination)
    }

    fun firebaseSignInWithEmail(email: String, password: String) = liveData(Dispatchers.IO) {
        useCasesAuth.signInWithEmailAndPassword(email, password).collect { result ->
            emit(result)
        }

    }

    fun firebaseRegisterWithEmail(email: String, password: String) = liveData(Dispatchers.IO) {
        useCasesAuth.signUp(email, password).collect { result ->
            emit(result)
        }
    }

    fun firebaseCreateRealtimeUser(email: String, password: String) = liveData(Dispatchers.IO) {
        useCasesAuth.addRealtimeUser(email, password).collect { result ->
            emit(result)
        }
    }

    fun resetPassword(email: String) = liveData(Dispatchers.IO) {
        useCasesAuth.resetPassword(email).collect { result ->
            emit(result)
        }
    }


}

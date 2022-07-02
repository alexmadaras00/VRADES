package com.example.vrades.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.vrades.domain.repositories.PreferencesRepository
import com.example.vrades.domain.use_cases.auth_repository.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCasesAuth: AuthUseCases,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    val authState = useCasesAuth.getAuthState()

    fun isAccountInAuth(email: String) =
        liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
            useCasesAuth.isAccountInAuth(email).collect { result -> emit(result) }
        }

    fun firebaseSignInWithEmail(email: String, password: String) =
        liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
            useCasesAuth.signInWithEmailAndPassword(email, password).collect { result ->
                emit(result)
            }

        }

     fun extractPreferences() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        preferencesRepository.getPreferences().collect {
            emit(it)
        }
    }

    fun firebaseRegisterWithEmail(email: String, password: String) =
        liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
            useCasesAuth.signUp(email, password).collect { result ->
                emit(result)
            }
        }

    fun firebaseCreateRealtimeUser(fullName: String) =
        liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
            useCasesAuth.addRealtimeUser(fullName).collect { result ->
                emit(result)
            }
        }


    fun resetPassword(email: String) = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        useCasesAuth.resetPassword(email).collect { result ->
            emit(result)
        }
    }


}

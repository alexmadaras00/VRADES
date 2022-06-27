package com.example.vrades.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vrades.domain.use_cases.auth_repository.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCasesAuth: AuthUseCases
) : ViewModel() {
    val authState = useCasesAuth.getAuthState()

    fun logOut() = liveData(Dispatchers.IO) {
        useCasesAuth.logOut().collect { result ->
            emit(result)
        }
    }

}
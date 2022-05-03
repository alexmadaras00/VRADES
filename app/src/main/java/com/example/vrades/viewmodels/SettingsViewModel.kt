package com.example.vrades.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.vrades.firebase.domain.use_cases.AuthUseCases
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
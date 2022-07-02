package com.example.vrades.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.vrades.domain.model.Preferences
import com.example.vrades.domain.repositories.PreferencesRepository
import com.example.vrades.domain.use_cases.auth_repository.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCasesAuth: AuthUseCases,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    fun logOut() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        useCasesAuth.logOut().collect { result ->
            emit(result)
        }
    }
    fun extractPreferences() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        preferencesRepository.getPreferences().collect { response ->
            emit(response)
        }
    }
    fun savePreferences(preferences: Preferences) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.savePreferences(preferences = preferences)
        }
    }

}
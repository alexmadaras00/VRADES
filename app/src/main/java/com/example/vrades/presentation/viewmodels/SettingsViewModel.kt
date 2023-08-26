package com.example.vrades.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.vrades.domain.model.Settings
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
    var displaySuggestion = MutableLiveData(false)
    var tutorialEnabled = MutableLiveData(false)
    var settings: MutableLiveData<Settings> = MutableLiveData()
    fun logOut() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        useCasesAuth.logOut().collect { result ->
            emit(result)
        }
    }

    fun extractPreferences() {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.getPreferences().collect { response ->
                settings.postValue(response)
            }
        }
    }

    fun savePreferences() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("PREF VM","PREF: ${displaySuggestion.value}")
            preferencesRepository.savePreferences(
                Settings(
                    displaySuggestions = displaySuggestion.value ?: false,
                    tutorialEnabled = tutorialEnabled.value ?: false
                )
            )
            Log.d("PREF VM","PREF: ${displaySuggestion.value}")
        }
    }
    fun clearPreferences(){

    }

}
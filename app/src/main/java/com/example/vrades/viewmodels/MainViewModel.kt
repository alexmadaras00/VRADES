package com.example.vrades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.vrades.firebase.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    useCasesAuth: AuthUseCases
) : ViewModel() {
    val authState = useCasesAuth.getAuthState()

    private val _onNavigateToTest = SingleLiveEvent<Void>()
    val onNavigateToTest: LiveData<Void>
        get() = _onNavigateToTest

    private fun onNavigateToTestClicked() {
        _onNavigateToTest.call()
    }




}

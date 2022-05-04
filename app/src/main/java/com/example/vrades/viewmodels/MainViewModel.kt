package com.example.vrades.viewmodels

import androidx.lifecycle.*
import com.example.vrades.firebase.domain.use_cases.AuthUseCases
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCasesAuth: AuthUseCases
) : ViewModel() {
    val authState = useCasesAuth.getAuthState().asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

    val isUserAuthenticated get() = useCasesAuth.isUserAuthenticated()

    private val _onNavigateToTest = SingleLiveEvent<Void>()
    val onNavigateToTest: LiveData<Void>
        get() = _onNavigateToTest

    private fun onNavigateToTestClicked() {
        _onNavigateToTest.call()
    }


}

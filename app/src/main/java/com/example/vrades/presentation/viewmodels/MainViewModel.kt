package com.example.vrades.presentation.viewmodels

import androidx.lifecycle.*
import com.example.vrades.domain.use_cases.auth_repository.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCasesAuth: AuthUseCases
) : ViewModel() {

    val isUserAuthenticated get() = useCasesAuth.isUserAuthenticated()


}

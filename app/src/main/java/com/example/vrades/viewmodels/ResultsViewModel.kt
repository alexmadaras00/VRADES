package com.example.vrades.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.vrades.firebase.domain.use_cases.auth_repository.AuthUseCases
import com.example.vrades.firebase.domain.use_cases.profile_repository.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private var results = ArrayList<Int>()

    fun getUser() = liveData(Dispatchers.IO) {
        profileUseCases.getUserById().collect {
            emit(it)
        }
    }
}
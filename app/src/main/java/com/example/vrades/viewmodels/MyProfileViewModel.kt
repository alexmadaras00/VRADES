package com.example.vrades.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vrades.firebase.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.firebase.domain.use_cases.vrades_repository.VradesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val vradesUseCases: VradesUseCases
) : ViewModel() {

    private fun addTests() {

    }

    fun getTests() = liveData(Dispatchers.IO) {
        profileUseCases.getTestsByUserId().collect {
            emit(it)
        }

    }
}
package com.example.vrades.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.vrades.domain.repositories.PreferencesRepository
import com.example.vrades.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.domain.use_cases.vrades_repository.VradesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val vradesUseCases: VradesUseCases,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    fun getUser() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        profileUseCases.getUserById().collect {
            withContext(Dispatchers.Main) {
                emit(it)
            }
        }

    }

    fun getEmotions() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        vradesUseCases.getEmotions().collect {
            emit(it)
        }
    }

    fun setProfilePictureInStorage(picture: Uri) = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        profileUseCases.setProfilePictureInStorage(picture).collect {
            emit(it)
        }
    }

    fun updateProfilePictureInRealtime(picture: String) = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        profileUseCases.updateProfilePictureInRealtime(picture).collect {
            emit(it)
        }
    }
    fun extractPreferences() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        preferencesRepository.getPreferences().collect {
            emit(it)
        }
    }


}
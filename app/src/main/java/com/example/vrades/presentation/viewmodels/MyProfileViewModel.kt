package com.example.vrades.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.vrades.firebase.domain.use_cases.profile_repository.ProfileUseCases
import com.example.vrades.firebase.domain.use_cases.vrades_repository.VradesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val vradesUseCases: VradesUseCases
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    fun getUser() = liveData(Dispatchers.IO) {
        profileUseCases.getUserById().collect {
            withContext(Dispatchers.Main) {
                emit(it)
            }
        }

    }

    fun getTests() = liveData(Dispatchers.IO) {
        profileUseCases.getTestsByUserId().collect {
            emit(it)
        }
    }


    fun getEmotions() = liveData(Dispatchers.IO + coroutineExceptionHandler) {
        vradesUseCases.getEmotions().collect {
            emit(it)
        }
    }

    fun setProfilePictureInStorage(picture: Uri) = liveData(Dispatchers.IO) {
        profileUseCases.setProfilePictureInStorage(picture).collect {
            emit(it)
        }
    }

    fun updateProfilePictureInRealtime(picture: String) = liveData(Dispatchers.IO) {
        profileUseCases.updateProfilePictureInRealtime(picture).collect {
            emit(it)
        }
    }


}
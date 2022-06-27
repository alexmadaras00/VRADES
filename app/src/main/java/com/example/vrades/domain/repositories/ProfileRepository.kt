package com.example.vrades.domain.repositories

import android.net.Uri
import com.example.vrades.domain.model.LifeHack
import com.example.vrades.domain.model.Response
import com.example.vrades.domain.model.Test
import com.example.vrades.domain.model.User
import dagger.Provides
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserById(): Flow<Response<User>>
    suspend fun getTestsByUserId(): Flow<Response<List<Test>>>
    suspend fun getAdvicesByUserId(): Flow<Response<List<LifeHack>>>
    suspend fun setProfilePictureInStorage(picture: Uri): Flow<Response<String>>
    suspend fun updateProfilePictureInRealtime(picture: String): Flow<Response<Boolean>>
    suspend fun addTestInRealtime(test: Test): Flow<Response<Boolean>>
    suspend fun generateAdvicesByTestResult() : Flow<Response<Boolean>>
    suspend fun setDetectedMediaInStorage(picture: Uri): Flow<Response<String>>
    suspend fun setDetectedAudioInStorage(audio: Uri): Flow<Response<String>>
}
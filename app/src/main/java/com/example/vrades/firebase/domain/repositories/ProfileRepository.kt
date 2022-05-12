package com.example.vrades.firebase.repositories.domain

import android.net.Uri
import com.example.vrades.model.LifeHack
import com.example.vrades.model.Response
import com.example.vrades.model.Test
import com.example.vrades.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserById(): Flow<Response<User>>
    suspend fun getTestsByUserId(): Flow<Response<List<Test>>>
    suspend fun getAdvicesByUserId(): Flow<Response<List<LifeHack>>>
    suspend fun getUserNameById(): Flow<Response<String>>
    suspend fun setProfilePictureInStorage(picture: Uri): Flow<Response<String>>
    suspend fun updateProfilePictureInRealtime(picture: String): Flow<Response<Boolean>>
}
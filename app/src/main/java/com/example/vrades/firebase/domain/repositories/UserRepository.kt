package com.example.vrades.firebase.repositories.domain

import com.example.vrades.model.Response
import com.example.vrades.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<Response<List<User>>>
    suspend fun getUserById(id: String): Flow<Response<User>>
}
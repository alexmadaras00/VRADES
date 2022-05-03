package com.example.vrades.firebase.repositories.data

import com.example.vrades.firebase.repositories.domain.UserRepository
import com.example.vrades.model.Response
import com.example.vrades.model.User
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val db: DatabaseReference): UserRepository {
    override suspend fun getUsers(): Flow<Response<List<User>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): Flow<Response<User>> {
        TODO("Not yet implemented")
    }

}
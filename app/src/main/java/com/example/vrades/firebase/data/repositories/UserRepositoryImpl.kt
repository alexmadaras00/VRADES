package com.example.vrades.firebase.repositories.data

import com.example.vrades.firebase.repositories.domain.UserRepository
import com.example.vrades.model.Response
import com.example.vrades.model.User
import com.example.vrades.utils.Constants
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    private val db: FirebaseDatabase,
    @Named(Constants.USERS_REF) private val usersRef: DatabaseReference
) : UserRepository {
    override suspend fun getUsers(): Flow<Response<List<User>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): Flow<Response<User>> {
        TODO("Not yet implemented")
    }

}
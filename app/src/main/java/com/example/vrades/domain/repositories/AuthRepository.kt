package com.example.vrades.firebase.repositories.auth

import com.example.vrades.model.Response
import com.example.vrades.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Flow<Response<Boolean>>
    suspend fun firebaseSignInWithGoogle(idToken: String):Flow<Response<Boolean>>
    suspend fun createUserInRealtime(fullName: String): Flow<Response<Boolean>>
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun sendPasswordResetEmail(email: String): Flow<Response<Boolean>>
    suspend fun register(email: String, password: String): Flow<Response<Boolean>>
    fun getUserAuthState(): Flow<Boolean>
    fun getUserProfile(): Flow<Response<User>>
    fun isAccountInAuth(email: String): Flow<Response<Int>>
    fun isUserAuthenticatedInFirebase(): Boolean

}
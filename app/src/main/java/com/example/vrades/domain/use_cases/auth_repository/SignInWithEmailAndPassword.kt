package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class SignInWithEmailAndPassword(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.firebaseSignInWithEmailAndPassword(email, password)
}
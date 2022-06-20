package com.example.vrades.firebase.domain.use_cases.auth_repository

import com.example.vrades.firebase.repositories.auth.AuthRepository

class SignInWithEmailAndPassword(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.firebaseSignInWithEmailAndPassword(email, password)
}
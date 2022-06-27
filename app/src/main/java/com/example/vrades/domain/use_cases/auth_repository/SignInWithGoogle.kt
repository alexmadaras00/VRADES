package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class SignInWithGoogle(private val repository: AuthRepository) {
    suspend operator fun invoke(idToken: String) = repository.firebaseSignInWithGoogle(idToken)

}

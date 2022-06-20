package com.example.vrades.firebase.domain.use_cases.auth_repository

import com.example.vrades.firebase.repositories.auth.AuthRepository

class SignUp(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.register(email, password)
}


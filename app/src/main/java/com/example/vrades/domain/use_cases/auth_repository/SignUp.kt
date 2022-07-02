package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class SignUp(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.register(email, password)
}


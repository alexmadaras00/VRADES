package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class ResetPassword(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) = repository.sendPasswordResetEmail(email)
}

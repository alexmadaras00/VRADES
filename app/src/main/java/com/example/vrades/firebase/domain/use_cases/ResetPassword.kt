package com.example.vrades.firebase.domain.use_cases

import com.example.vrades.firebase.repositories.auth.AuthRepository

class ResetPassword(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) = repository.sendPasswordResetEmail(email)
}

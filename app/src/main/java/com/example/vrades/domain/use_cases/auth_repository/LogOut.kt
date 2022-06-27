package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class LogOut(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.signOut()
}
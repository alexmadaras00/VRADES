package com.example.vrades.firebase.domain.use_cases.auth_repository

import com.example.vrades.firebase.repositories.auth.AuthRepository

class LogOut(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.signOut()
}
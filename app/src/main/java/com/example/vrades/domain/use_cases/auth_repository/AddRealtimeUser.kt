package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class AddRealtimeUser(private val repository: AuthRepository) {
    suspend operator fun invoke(fullName: String) = repository.createUserInRealtime(fullName)
} // call with function name = "invoke()"

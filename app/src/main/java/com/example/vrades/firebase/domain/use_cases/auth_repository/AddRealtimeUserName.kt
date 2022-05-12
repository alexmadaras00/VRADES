package com.example.vrades.firebase.domain.use_cases.auth_repository

import com.example.vrades.firebase.repositories.auth.AuthRepository

class AddRealtimeUserName(private val repository: AuthRepository) {
    suspend operator fun invoke(fullName: String) = repository.createUserNameInRealtime(fullName)
}

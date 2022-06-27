package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class GetUserProfile(private val repository: AuthRepository) {
    operator fun invoke() = repository.getUserProfile()
}

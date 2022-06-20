package com.example.vrades.firebase.domain.use_cases.auth_repository

import com.example.vrades.firebase.repositories.auth.AuthRepository

class GetUserProfile(private val repository: AuthRepository) {
    operator fun invoke() = repository.getUserProfile()
}

package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class GetAuthState(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getUserAuthState()
}


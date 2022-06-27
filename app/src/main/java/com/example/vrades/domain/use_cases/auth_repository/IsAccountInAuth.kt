package com.example.vrades.domain.use_cases.auth_repository

import com.example.vrades.domain.repositories.AuthRepository

class IsAccountInAuth(private val repository: AuthRepository) {
    operator fun invoke(email: String) = repository.isAccountInAuth(email)
}
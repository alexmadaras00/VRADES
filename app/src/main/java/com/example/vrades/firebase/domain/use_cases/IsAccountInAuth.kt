package com.example.vrades.firebase.domain.use_cases

import com.example.vrades.firebase.repositories.auth.AuthRepository

class IsAccountInAuth(private val repository: AuthRepository) {
    operator fun invoke(email: String) = repository.isAccountInAuth(email)
}
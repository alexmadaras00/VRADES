package com.example.vrades.firebase.domain.use_cases

import com.example.vrades.firebase.repositories.auth.AuthRepository

class GetAuthState(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getUserAuthState()
}


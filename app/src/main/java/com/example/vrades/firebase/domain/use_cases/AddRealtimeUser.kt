package com.example.vrades.firebase.domain.use_cases

import com.example.vrades.firebase.repositories.auth.AuthRepository

class AddRealtimeUser(private val repository: AuthRepository) {
    suspend operator fun invoke(fullName: String) = repository.createUserInRealtime(fullName)
} // call with function name = "invoke()"

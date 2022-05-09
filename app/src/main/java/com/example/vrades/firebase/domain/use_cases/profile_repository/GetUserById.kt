package com.example.vrades.firebase.domain.use_cases.profile_repository

import com.example.vrades.firebase.repositories.domain.ProfileRepository

class GetUserById(private val repository: ProfileRepository) {
    suspend operator fun invoke() = repository.getUserById()
}
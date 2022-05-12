package com.example.vrades.firebase.domain.use_cases.profile_repository

import com.example.vrades.firebase.repositories.domain.ProfileRepository

class GetUserNameById(private val repository: ProfileRepository) {
    suspend operator fun invoke() = repository.getUserNameById()

}

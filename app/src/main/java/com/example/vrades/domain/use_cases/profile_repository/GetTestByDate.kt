package com.example.vrades.domain.use_cases.profile_repository

import com.example.vrades.domain.repositories.ProfileRepository

class GetTestByDate(private val repository: ProfileRepository) {
    suspend operator fun invoke(date: String) = repository.getTestByDate(date)
}

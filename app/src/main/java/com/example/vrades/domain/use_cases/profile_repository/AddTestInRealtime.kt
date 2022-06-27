package com.example.vrades.domain.use_cases.profile_repository

import com.example.vrades.domain.repositories.ProfileRepository
import com.example.vrades.domain.model.Test

class AddTestInRealtime(private val repository: ProfileRepository) {
    suspend operator fun invoke(test: Test) = repository.addTestInRealtime(test)

}

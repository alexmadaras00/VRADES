package com.example.vrades.firebase.domain.use_cases.profile_repository

import com.example.vrades.firebase.repositories.domain.ProfileRepository
import com.example.vrades.model.Test

class AddTestInRealtime(private val repository: ProfileRepository) {
    suspend operator fun invoke(test: Test) = repository.addTestInRealtime(test)

}

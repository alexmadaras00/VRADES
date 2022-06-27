package com.example.vrades.domain.use_cases.vrades_repository

import com.example.vrades.domain.repositories.VradesRepository

class GetLifeHacks(private val repository: VradesRepository) {
    suspend operator fun invoke() = repository.getLifeHacks()
}
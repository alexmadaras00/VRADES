package com.example.vrades.domain.use_cases.vrades_repository

import com.example.vrades.domain.repositories.VradesRepository

class GetDataAudioTest(private val repository: VradesRepository) {
    suspend operator fun invoke() = repository.getDataAudioTest()
}
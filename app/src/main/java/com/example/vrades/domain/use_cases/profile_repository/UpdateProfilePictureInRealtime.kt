package com.example.vrades.domain.use_cases.profile_repository

import com.example.vrades.domain.repositories.ProfileRepository

class UpdateProfilePictureInRealtime(private val repository: ProfileRepository) {
    suspend operator fun invoke(picture: String) = repository.updateProfilePictureInRealtime(picture)
}

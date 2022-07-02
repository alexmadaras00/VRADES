package com.example.vrades.domain.use_cases.profile_repository

import android.net.Uri
import com.example.vrades.domain.repositories.ProfileRepository

class SetDetectedMediaInStorage(private val repository: ProfileRepository) {
    suspend operator fun invoke(picture: Uri) = repository.setDetectedMediaInStorage(picture)
}

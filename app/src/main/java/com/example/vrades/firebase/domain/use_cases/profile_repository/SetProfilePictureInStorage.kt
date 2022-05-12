package com.example.vrades.firebase.domain.use_cases.profile_repository

import android.net.Uri
import com.example.vrades.firebase.repositories.domain.ProfileRepository

class SetProfilePictureInStorage(private val repository: ProfileRepository) {
    suspend operator fun invoke(picture: Uri) = repository.setProfilePictureInStorage(picture)
}

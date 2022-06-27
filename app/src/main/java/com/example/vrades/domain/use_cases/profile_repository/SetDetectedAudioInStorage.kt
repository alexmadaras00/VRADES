package com.example.vrades.domain.use_cases.profile_repository

import android.net.Uri
import com.example.vrades.domain.repositories.ProfileRepository

class SetDetectedAudioInStorage(private val repository: ProfileRepository) {
    suspend operator fun invoke(audio: Uri) = repository.setDetectedAudioInStorage(audio)
}

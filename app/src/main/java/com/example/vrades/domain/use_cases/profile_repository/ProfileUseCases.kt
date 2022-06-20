package com.example.vrades.firebase.domain.use_cases.profile_repository

import com.example.vrades.domain.use_cases.profile_repository.SetDetectedPictureInStorage

data class ProfileUseCases(
    val getUserById: GetUserById,
    val getLifeHacksByUserId: GetLifeHacksByUserId,
    val getTestsByUserId: GetTestsByUserId,
    val setProfilePictureInStorage: SetProfilePictureInStorage,
    val setDetectedPictureInStorage: SetDetectedPictureInStorage,
    val updateProfilePictureInRealtime: UpdateProfilePictureInRealtime,
    val addTestInRealtime : AddTestInRealtime,
    val generateAdvicesByTestResult: GenerateAdvicesByTestResult
) {
}
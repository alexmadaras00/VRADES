package com.example.vrades.domain.use_cases.profile_repository

data class ProfileUseCases(
    val getUserById: GetUserById,
    val getLifeHacksByUserId: GetLifeHacksByUserId,
    val getTestsByUserId: GetTestsByUserId,
    val setProfilePictureInStorage: SetProfilePictureInStorage,
    val setDetectedMediaInStorage: SetDetectedMediaInStorage,
    val setDetectedAudioInStorage: SetDetectedAudioInStorage,
    val updateProfilePictureInRealtime: UpdateProfilePictureInRealtime,
    val addTestInRealtime : AddTestInRealtime,
    val generateAdvicesByTestResult: GenerateAdvicesByTestResult
) {
}
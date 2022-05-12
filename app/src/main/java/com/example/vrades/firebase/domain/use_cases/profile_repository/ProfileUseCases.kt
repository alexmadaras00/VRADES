package com.example.vrades.firebase.domain.use_cases.profile_repository

data class ProfileUseCases(
    val getUserById: GetUserById,
    val getLifeHacksByUserId: GetLifeHacksByUserId,
    val getTestsByUserId: GetTestsByUserId,
    val getUserNameById: GetUserNameById,
    val setProfilePictureInStorage: SetProfilePictureInStorage,
    val updateProfilePictureInRealtime: UpdateProfilePictureInRealtime
) {
}
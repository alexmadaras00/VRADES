package com.example.vrades.firebase.domain.use_cases.auth_repository

data class AuthUseCases(
    val resetPassword: ResetPassword,
    val addRealtimeUser: AddRealtimeUser,
    val getUserProfile: GetUserProfile,
    val getAuthState: GetAuthState,
    val signInWithGoogle: SignInWithGoogle,
    val logOut: LogOut,
    val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    val signUp: SignUp,
    val isAccountInAuth: IsAccountInAuth,
    val isUserAuthenticated: IsUserAuthenticated
)

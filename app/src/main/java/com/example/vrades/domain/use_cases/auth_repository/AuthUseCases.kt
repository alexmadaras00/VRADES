package com.example.vrades.domain.use_cases.auth_repository

data class AuthUseCases(
    val resetPassword: ResetPassword,
    val addRealtimeUser: AddRealtimeUser,
    val getUserProfile: GetUserProfile,
    val getAuthState: GetAuthState,
    val logOut: LogOut,
    val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    val signUp: SignUp,
    val isAccountInAuth: IsAccountInAuth,
    val isUserAuthenticated: IsUserAuthenticated
)

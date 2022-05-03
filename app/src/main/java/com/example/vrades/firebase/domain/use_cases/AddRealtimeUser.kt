package com.example.vrades.firebase.domain.use_cases

import com.example.vrades.firebase.repositories.auth.AuthRepository

class AddRealtimeUser(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.firebaseSignInWithEmailAndPassword(email, password)
} // call with function name = "invoke()"

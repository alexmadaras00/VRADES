package com.example.vrades.utils

import com.example.vrades.firebase.repositories.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth

object Constants {

    const val ERROR_REF: String = "Unexpected error!"

    const val REALTIME_DATABASE = "Realtime Database"

    const val APP="CONTEXT"

    //References
    const val USERS_REF: String = "users"
    const val EMOTIONS_REF: String = "emotions"
    const val LIFEHACKS_REF = "lifeHacks"
    const val LIFEHACKS_NAME_REF = "lifeHacksName"

    //Fields
    const val NAME = "email"
    const val EMAIL = "email"
    const val OCCUPATION = "occupation"
    const val AGE = "age"
    const val TESTS = "tests"
    const val ADVICES = "advices"
    const val IS_TUTORIAL_ENABLED = "is_tutorial_enabled"

}
package com.example.vrades.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val displaySuggestions: Boolean,
    val tutorialEnabled: Boolean
)
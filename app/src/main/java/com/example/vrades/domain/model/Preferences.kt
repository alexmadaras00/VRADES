package com.example.vrades.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Preferences(
    val displaySuggestions: String = "OFF",
    val tutorialEnabled: String = "OFF"
)
package com.example.vrades.domain.repositories

import com.example.vrades.domain.model.Preferences
import kotlinx.coroutines.flow.Flow


interface PreferencesRepository {
    suspend fun getPreferences(): Flow<Preferences>
    suspend fun savePreferences(preferences: Preferences)
}
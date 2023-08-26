package com.example.vrades.domain.repositories

import com.example.vrades.domain.model.Settings
import kotlinx.coroutines.flow.Flow


interface PreferencesRepository {
    suspend fun getPreferences(): Flow<Settings>
    suspend fun savePreferences(settings: Settings)
}
package com.example.vrades.data.repositories

import android.content.Context
import androidx.datastore.dataStore
import com.example.vrades.data.preferences.PreferencesSerializer
import com.example.vrades.domain.model.Preferences
import com.example.vrades.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

val Context.dataStore by dataStore("preferences.json", PreferencesSerializer)

@Singleton
class PreferencesRepositoryImpl(private val context: Context) : PreferencesRepository {

    override suspend fun savePreferences(preferences: Preferences) {
        context.dataStore.updateData {
            it.copy(
                displaySuggestions = it.displaySuggestions,
                tutorialEnabled = it.tutorialEnabled
            )
        }
        println("PREF: $preferences")
    }

    override suspend fun getPreferences(): Flow<Preferences> = context.dataStore.data.map {
        Preferences(
            displaySuggestions = it.displaySuggestions,
            tutorialEnabled = it.tutorialEnabled
        )
    }
}
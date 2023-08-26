package com.example.vrades.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.vrades.domain.model.Settings
import com.example.vrades.domain.repositories.PreferencesRepository
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

@Singleton
class PreferencesRepositoryImpl(private val context: Context) : PreferencesRepository {
    companion object {
        val TUTORIAL_ENABLED = booleanPreferencesKey("TUTORIAL")
        val DISPLAY_SUGGESTIONS = booleanPreferencesKey("SUGGESTIONS")
    }

    override suspend fun savePreferences(settings: Settings) {
        context.dataStore.edit {
            it[TUTORIAL_ENABLED] = settings.tutorialEnabled
            it[DISPLAY_SUGGESTIONS] = settings.displaySuggestions
        }
        Log.d("PREFERENCES","PREF: ${settings.displaySuggestions}")
    }

    override suspend fun getPreferences(): Flow<Settings> = context.dataStore.data.map {
        Log.d("PREFERENCES2","PREF: ${it[DISPLAY_SUGGESTIONS]}")

        Settings(
            displaySuggestions = it[DISPLAY_SUGGESTIONS]?:false,
            tutorialEnabled = it[TUTORIAL_ENABLED]?:false
        )
    }
}
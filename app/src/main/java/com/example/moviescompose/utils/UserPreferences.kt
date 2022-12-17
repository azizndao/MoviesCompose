package com.example.moviescompose.utils

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.moviescompose.data.model.SortKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    private val dataStore = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
    ) {
        context.preferencesDataStoreFile("user_settings.pb")
    }

    fun sortFlow() = dataStore.data.map { preferences ->
        val key = preferences[keySortKey]
            ?: SortKey.POPULARITY.key

        val sortKey = SortKey.values().find { it.key == key }!!.key
        val sortAscendant = preferences[keySortAscendant] ?: false
        sortKey to sortAscendant
    }

    suspend fun setSort(key: String, ascendant: Boolean) {
        dataStore.edit { preferences ->
            preferences[keySortAscendant] =
                if (preferences[keySortKey] == key) !ascendant else ascendant
            preferences[keySortKey] = key
        }
    }

    fun getTranslation(): Flow<String> = dataStore.data.map { it[keyTranslation] ?: "en-US" }

    suspend fun setTranslation(code: String) = dataStore.edit { it[keyTranslation] = code }
}


private val keySortKey = stringPreferencesKey("sort_key")
private val keySortAscendant = booleanPreferencesKey("sort_ascendant")
private val keyTranslation = stringPreferencesKey("current_translation")
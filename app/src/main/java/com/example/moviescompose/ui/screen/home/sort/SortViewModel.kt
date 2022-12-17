package com.example.moviescompose.ui.screen.home.sort

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.moviescompose.data.model.SortKey
import com.example.moviescompose.utils.UserPreferences
import kotlinx.coroutines.flow.map

class SortViewModel(
    private val preferences: UserPreferences,
    app: Application
) : AndroidViewModel(app) {

    val uiStateFlow = preferences.sortFlow().map { (key, ascendant) ->
        SortKey.values().map {
            MovieSortUiState(
                key = it.key,
                label = app.getString(it.labelId),
                selected = it.key == key,
                ascendant = ascendant,
            )
        }
    }

    suspend fun setSort(uiState: MovieSortUiState) {
        preferences.setSort(uiState.key, uiState.ascendant)
    }
}
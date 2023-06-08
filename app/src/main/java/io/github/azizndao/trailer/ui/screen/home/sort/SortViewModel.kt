package io.github.azizndao.trailer.ui.screen.home.sort

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.azizndao.trailer.data.model.SortKey
import io.github.azizndao.trailer.utils.UserPreferences
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
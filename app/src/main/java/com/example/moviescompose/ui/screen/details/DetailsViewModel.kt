package com.example.moviescompose.ui.screen.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviescompose.data.api.MovieApiService
import com.example.moviescompose.data.api.TVApiService
import com.example.moviescompose.data.model.ITEM_TYPE_MOVIE
import com.example.moviescompose.data.model.ITEM_TYPE_TV_SHOW
import com.example.moviescompose.data.model.ItemType
import com.example.moviescompose.data.model.Movie
import com.example.moviescompose.data.model.TV
import com.example.moviescompose.data.model.toUiState
import com.example.moviescompose.utils.UserPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(
  private val id: Long,
  @ItemType val type: String,
  private val movieApiService: MovieApiService,
  private val tvApiService: TVApiService,
  private val preferences: UserPreferences,
) : ViewModel() {

  var detailsUiState by mutableStateOf<DetailsUiState>(DetailsUiState.Loading)
    private set

  init {
    refresh()
  }

  private var job: Job? = null

  fun refresh() {
    detailsUiState = DetailsUiState.Loading
    job?.cancel()
    job = viewModelScope.launch {
      detailsUiState = try {
        val success = when (type) {
          ITEM_TYPE_MOVIE -> movieApiService.getMovieDetails(
            id,
            preferences.getTranslation().first()
          ).toUiState(getSimilarItems())

          else -> tvApiService.getTvDetails(
            id,
            preferences.getTranslation().first()
          ).toUiState(getSimilarItems())
        }
        Timber.e(success.toString())
        success
      } catch (e: Exception) {
        DetailsUiState.Error(e)
      }
    }
  }

  private suspend fun getSimilarItems() = when (type) {
    ITEM_TYPE_MOVIE -> movieApiService.getSimilarMovies(
      id = id,
      page = 1,
      language = preferences.getTranslation().first()
    ).results.map(Movie::toUiState)

    ITEM_TYPE_TV_SHOW -> tvApiService.getSimilarTvs(
      id = id,
      page = 1,
      language = preferences.getTranslation().first()
    ).results.map(TV::toUiState)

    else -> throw IllegalArgumentException()
  }

}
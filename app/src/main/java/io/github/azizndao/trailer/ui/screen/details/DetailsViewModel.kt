package io.github.azizndao.trailer.ui.screen.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.azizndao.model.ITEM_TYPE_MOVIE
import io.github.azizndao.model.ITEM_TYPE_TV_SHOW
import io.github.azizndao.model.ItemType
import io.github.azizndao.model.Movie
import io.github.azizndao.model.TVShow
import io.github.azizndao.network.MovieApiService
import io.github.azizndao.network.TVApiService
import io.github.azizndao.trailer.data.model.toUiState
import io.github.azizndao.trailer.utils.UserPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(
  private val id: Long,
  @ItemType val type: String,
  private val movieApiService: io.github.azizndao.network.MovieApiService,
  private val tvApiService: io.github.azizndao.network.TVApiService,
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
    ).results.map(TVShow::toUiState)

    else -> throw IllegalArgumentException()
  }

}
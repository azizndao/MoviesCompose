package io.github.azizndao.trailer.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import io.github.azizndao.model.Movie
import io.github.azizndao.model.TVShow
import io.github.azizndao.network.MovieApiService
import io.github.azizndao.network.TVApiService
import io.github.azizndao.trailer.data.datasources.TheMoviesPagingSource
import io.github.azizndao.trailer.data.model.toUiState
import io.github.azizndao.trailer.utils.UiState
import io.github.azizndao.trailer.utils.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
  private val apiService: io.github.azizndao.network.MovieApiService,
  private val tvApiService: io.github.azizndao.network.TVApiService,
  preferences: UserPreferences
) : ViewModel() {

  var uiState: UiState<HomeUiState> by mutableStateOf(UiState.Loading)
    private set

  init {
    refresh()
  }

  fun refresh() {
    viewModelScope.launch {
      uiState = try {
        val popularMovie = apiService.getPopular(1).results.first()

        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = true)

        val moviePagingFlow = Pager(pagingConfig) {
          TheMoviesPagingSource { apiService.getPopular(it) }
        }.flow.map { it.map(Movie::toUiState) }.cachedIn(viewModelScope)

        val tvShowPagingFlow = Pager(pagingConfig) {
          TheMoviesPagingSource { tvApiService.getPopular(it) }
        }.flow.map { it.map(TVShow::toUiState) }.cachedIn(viewModelScope)

        UiState.Success(
          HomeUiState(
            popularMovie = popularMovie,
            moviePagingItems = moviePagingFlow,
            tvPagingItems = tvShowPagingFlow
          )
        )
      } catch (e: Exception) {
        Timber.e(e)
        UiState.Error(e)
      }
    }
  }

  val sortFlow = preferences.sortFlow().stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
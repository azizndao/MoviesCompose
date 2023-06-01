package com.example.moviescompose.ui.screen.movie

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviescompose.R
import com.example.moviescompose.data.api.MovieApiService
import com.example.moviescompose.data.datasources.TheMoviesPagingSource
import com.example.moviescompose.data.model.Movie
import com.example.moviescompose.data.model.toUiState
import kotlinx.coroutines.flow.map

class MovieViewModel(
  private val apiService: MovieApiService,
  app: Application
) : AndroidViewModel(app) {

  var selectedTabIndex: Int by mutableIntStateOf(0)
    private set

  private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = true)

  var tabViews = mapOf(
    R.string.popular to Pager(pagingConfig) {
      TheMoviesPagingSource { apiService.getPopular(it) }
    }.flow.map { it.map(Movie::toUiState) }.cachedIn(viewModelScope),

    R.string.now_playing to Pager(pagingConfig) {
      TheMoviesPagingSource { apiService.getNowPlaying(it) }
    }.flow.map { it.map(Movie::toUiState) }.cachedIn(viewModelScope),

    R.string.upcoming to Pager(pagingConfig) {
      TheMoviesPagingSource { apiService.getUpcoming(it) }
    }.flow.map { it.map(Movie::toUiState) }.cachedIn(viewModelScope),

    R.string.top_rated to Pager(pagingConfig) {
      TheMoviesPagingSource { apiService.getTopRated(it) }
    }.flow.map { it.map(Movie::toUiState) }.cachedIn(viewModelScope),
  )


  fun selectTab(index: Int) {
    selectedTabIndex = index
  }
}
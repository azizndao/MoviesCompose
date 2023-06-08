package io.github.azizndao.trailer.ui.screen.movie

import android.app.Application
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.github.azizndao.model.Movie
import io.github.azizndao.model.TVShow
import io.github.azizndao.trailer.R
import io.github.azizndao.network.MovieApiService
import io.github.azizndao.trailer.data.datasources.TheMoviesPagingSource
import io.github.azizndao.trailer.data.model.CardUiState
import io.github.azizndao.trailer.data.model.toUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieViewModel(
  private val apiService: MovieApiService,
  app: Application
) : AndroidViewModel(app) {

  val tabs = listOf(R.string.popular, R.string.now_playing, R.string.upcoming, R.string.top_rated)

  var selectedTabIndex: Int by mutableIntStateOf(0)
    private set

  val selectedTabView by derivedStateOf { tabViews[tabs[selectedTabIndex]]!! }

  private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = true)

  private var tabViews = listOf(
    TheMoviesPagingSource { apiService.getPopular(it) },
    TheMoviesPagingSource { apiService.getNowPlaying(it) },
    TheMoviesPagingSource { apiService.getUpcoming(it) },
    TheMoviesPagingSource { apiService.getTopRated(it) },
    TheMoviesPagingSource { apiService.getTopRated(it) }
  ).map { pagingSource ->
    Pager(pagingConfig) { pagingSource }
      .flow.map { it.map(Movie::toUiState) }.cachedIn(viewModelScope)
  }

  fun selectTab(index: Int) {
    selectedTabIndex = index
  }

  fun getPageItems(index: Int): Flow<PagingData<CardUiState>> {
    return tabViews[index]
  }
}
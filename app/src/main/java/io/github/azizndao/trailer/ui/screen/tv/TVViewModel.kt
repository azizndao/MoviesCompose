package io.github.azizndao.trailer.ui.screen.tv

import android.app.Application
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
import io.github.azizndao.model.TVShow
import io.github.azizndao.trailer.data.datasources.TheMoviesPagingSource
import io.github.azizndao.trailer.data.model.CardUiState
import io.github.azizndao.trailer.data.model.toUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TVViewModel(
  private val apiService: io.github.azizndao.network.TVApiService,
  app: Application
) : AndroidViewModel(app) {

  var selectedTabIndex: Int by mutableIntStateOf(0)
    private set

  private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = true)

  private var tabViews = listOf(
    TheMoviesPagingSource { apiService.getPopular(it) },
    TheMoviesPagingSource { apiService.getAiringToday(it) },
    TheMoviesPagingSource { apiService.getOnTv(it) },
    TheMoviesPagingSource { apiService.getTopRated(it) }
  ).map { pagingSource ->
    Pager(pagingConfig) { pagingSource }
      .flow.map { it.map(TVShow::toUiState) }.cachedIn(viewModelScope)
  }


  fun selectTab(index: Int) {
    selectedTabIndex = index
  }

  fun getPageItems(index: Int): Flow<PagingData<CardUiState>> {
    return tabViews[index]
  }
}
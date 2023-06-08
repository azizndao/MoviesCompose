package io.github.azizndao.trailer.ui.screen.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.azizndao.network.MovieApiService
import io.github.azizndao.trailer.data.datasources.MovieSearchPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(
  private val apiRepository: io.github.azizndao.network.MovieApiService,
  app: Application
) : AndroidViewModel(app) {


  private val searchQuery = MutableStateFlow("")

  @OptIn(ExperimentalCoroutinesApi::class)
  val dataFlow = searchQuery.flatMapLatest { query ->
    Pager(PagingConfig(pageSize = 20)) { MovieSearchPagingSource(query, apiRepository) }
      .flow.cachedIn(viewModelScope)
  }

  fun updateQuery(value: String) {
    searchQuery.value = value
  }
}
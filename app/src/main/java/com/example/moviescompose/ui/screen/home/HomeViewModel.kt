package com.example.moviescompose.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviescompose.data.api.MovieApiService
import com.example.moviescompose.data.api.TVApiService
import com.example.moviescompose.data.datasources.TheMoviesPagingSource
import com.example.moviescompose.data.model.Movie
import com.example.moviescompose.data.model.TV
import com.example.moviescompose.data.model.toUiState
import com.example.moviescompose.utils.UiState
import com.example.moviescompose.utils.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val apiService: MovieApiService,
    private val tvApiService: TVApiService,
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

                val tvPagingFlow = Pager(pagingConfig) {
                    TheMoviesPagingSource { tvApiService.getPopular(it) }
                }.flow.map { it.map(TV::toUiState) }.cachedIn(viewModelScope)

                UiState.Success(
                    HomeUiState(
                        popularMovie = popularMovie,
                        moviePagingItems = moviePagingFlow,
                        tvPagingItems = tvPagingFlow
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
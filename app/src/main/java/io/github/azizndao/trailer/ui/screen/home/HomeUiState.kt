package io.github.azizndao.trailer.ui.screen.home

import androidx.paging.PagingData
import io.github.azizndao.model.Movie
import io.github.azizndao.trailer.data.model.CardUiState
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
  val popularMovie: Movie,
  val moviePagingItems: Flow<PagingData<CardUiState>>,
  val tvPagingItems: Flow<PagingData<CardUiState>>,
)

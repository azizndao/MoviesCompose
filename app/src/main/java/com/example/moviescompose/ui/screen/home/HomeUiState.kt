package com.example.moviescompose.ui.screen.home

import androidx.paging.PagingData
import com.example.moviescompose.data.model.CardUiState
import com.example.moviescompose.data.model.Movie
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val popularMovie: Movie,
    val moviePagingItems: Flow<PagingData<CardUiState>>,
    val tvPagingItems: Flow<PagingData<CardUiState>>,
)

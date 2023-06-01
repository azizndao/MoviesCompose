package com.example.moviescompose.ui.screen.details

import com.example.moviescompose.data.model.MovieDetails
import com.example.moviescompose.data.model.Video

data class MovieDetailsUiState(
  val details: MovieDetails,
  val videos: List<Video>
)

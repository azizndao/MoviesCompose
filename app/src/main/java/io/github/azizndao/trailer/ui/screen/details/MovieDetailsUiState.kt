package io.github.azizndao.trailer.ui.screen.details

import io.github.azizndao.model.MovieDetails
import io.github.azizndao.model.Video

data class MovieDetailsUiState(
  val details: MovieDetails,
  val videos: List<Video>
)

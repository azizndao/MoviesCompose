package io.github.azizndao.trailer.data.model

import io.github.azizndao.model.Movie
import io.github.azizndao.model.TVShow


data class CardUiState(
  val id: Long,
  val title: String,
  val date: String?,
  val posterPath: String?,
  val voteAverage: Double,
)

fun Movie.toUiState() = CardUiState(
  id = id,
  title = title,
  date = releaseDate,
  posterPath = posterPath,
  voteAverage = voteAverage
)

fun TVShow.toUiState() = CardUiState(
  id = id,
  title = name,
  date = firstAirDate,
  posterPath = posterPath,
  voteAverage = voteAverage
)

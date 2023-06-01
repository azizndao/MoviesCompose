package com.example.moviescompose.data.model


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

fun TV.toUiState() = CardUiState(
  id = id,
  title = name,
  date = firstAirDate,
  posterPath = posterPath,
  voteAverage = voteAverage
)

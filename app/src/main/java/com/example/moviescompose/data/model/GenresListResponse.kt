package com.example.moviescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GenresListResponse(
  val genres: List<Genre>
)

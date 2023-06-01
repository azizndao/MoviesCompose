package com.example.moviescompose.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Season(
  @SerialName("air_date")
  val airDate: String? = null,

  @SerialName("episode_count")
  val episodeCount: Long,

  val id: Long,
  val name: String,
  val overview: String,

  @SerialName("poster_path")
  val posterPath: String? = null,

  @SerialName("season_number")
  val seasonNumber: Long
)

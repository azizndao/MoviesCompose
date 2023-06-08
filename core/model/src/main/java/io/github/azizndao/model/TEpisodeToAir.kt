package io.github.azizndao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TEpisodeToAir(
  @SerialName("air_date")
  val airDate: String,

  @SerialName("episode_number")
  val episodeNumber: Long,

  val id: Long,
  val name: String,
  val overview: String,

  @SerialName("production_code")
  val productionCode: String,

  val runtime: Int? = null,

  @SerialName("season_number")
  val seasonNumber: Long,

  @SerialName("show_id")
  val showID: Long,

  @SerialName("still_path")
  val stillPath: String? = null,

  @SerialName("vote_average")
  val voteAverage: Double,

  @SerialName("vote_count")
  val voteCount: Long
)
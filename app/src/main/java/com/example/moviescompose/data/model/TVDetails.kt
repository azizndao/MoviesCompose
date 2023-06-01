package com.example.moviescompose.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

@Serializable
data class TVDetails(
  val adult: Boolean,

  @SerialName("backdrop_path")
  val backdropPath: String,

  @SerialName("created_by")
  val createdBy: List<Creator>,

  @SerialName("episode_run_time")
  val episodeRunTime: List<Int>,

  @SerialName("first_air_date")
  val firstAirDate: String,

  val genres: List<Genre>,
  val homepage: String,
  val id: Long,

  @SerialName("in_production")
  val inProduction: Boolean,

  val languages: List<String>,

  @SerialName("last_air_date")
  val lastAirDate: String,

  @SerialName("last_episode_to_air")
  val lastEpisodeToAir: TEpisodeToAir,

  val name: String,

  @SerialName("next_episode_to_air")
  val nextEpisodeToAir: TEpisodeToAir? = null,

  val networks: List<Network>,

  @SerialName("number_of_episodes")
  val numberOfEpisodes: Long,

  @SerialName("number_of_seasons")
  val numberOfSeasons: Long,

  @SerialName("origin_country")
  val originCountry: List<String>,

  @SerialName("original_language")
  val originalLanguage: String,

  @SerialName("original_name")
  val originalName: String,

  val overview: String,
  val popularity: Double,

  @SerialName("poster_path")
  val posterPath: String,

  @SerialName("production_companies")
  val productionCompanies: JsonArray,

  @SerialName("production_countries")
  val productionCountries: JsonArray,

  val seasons: List<Season>,

  @SerialName("spoken_languages")
  val spokenLanguages: List<SpokenLanguage>,

  val status: String,
  val tagline: String,
  val type: String,

  @SerialName("vote_average")
  val voteAverage: Double,

  @SerialName("vote_count")
  val voteCount: Long,

  val videos: VideoResponse,

  val images: ImageResponse,
)

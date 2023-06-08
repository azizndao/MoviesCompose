package io.github.azizndao.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Movie(
  val id: Long,

  val adult: Boolean,

  @SerialName("backdrop_path")
  val backdropPath: String? = null,

  @SerialName("genre_ids")
  val genreIDS: List<Long>,

  @SerialName("original_language")
  val originalLanguage: String,

  @SerialName("original_title")
  val originalTitle: String,

  val overview: String,
  val popularity: Double,

  @SerialName("poster_path")
  val posterPath: String? = null,

  @SerialName("release_date")
  val releaseDate: String? = null,

  val title: String,
  val video: Boolean,

  @SerialName("vote_average")
  val voteAverage: Double,

  @SerialName("vote_count")
  val voteCount: Long
)

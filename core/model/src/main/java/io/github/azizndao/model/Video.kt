package io.github.azizndao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
  @SerialName("iso_639_1")
  val iso6391: String,

  @SerialName("iso_3166_1")
  val iso31661: String,

  val name: String,
  val key: String,
  val site: String,
  val size: Long,
  val type: String,
  val official: Boolean,

  @SerialName("published_at")
  val publishedAt: String,

  val id: String
)



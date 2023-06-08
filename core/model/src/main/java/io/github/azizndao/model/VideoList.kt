package io.github.azizndao.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoList(
  val id: Long? = null,
  val results: List<Video>
)

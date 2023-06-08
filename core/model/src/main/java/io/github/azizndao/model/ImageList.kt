package io.github.azizndao.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageList(
  val id: Long? = null,
  val backdrops: List<Image>,
  val logos: List<Image>,
  val posters: List<Image>
)

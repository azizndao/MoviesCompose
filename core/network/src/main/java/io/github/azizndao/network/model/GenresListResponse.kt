package io.github.azizndao.network.model

import io.github.azizndao.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenresListResponse(
  val genres: List<Genre>
)

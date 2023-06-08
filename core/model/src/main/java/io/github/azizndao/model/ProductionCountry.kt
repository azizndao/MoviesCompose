package io.github.azizndao.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
  @SerialName("iso_3166_1")
  val isoCode: String,

  val name: String
)
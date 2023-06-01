package com.example.moviescompose.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
  val id: Long,

  @SerialName("logo_path")
  val logoPath: String? = null,

  val name: String,

  @SerialName("origin_country")
  val originCountry: String
)
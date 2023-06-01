package com.example.moviescompose.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Network(
  val id: Long,
  val name: String,

  @SerialName("logo_path")
  val logoPath: String? = null,

  @SerialName("origin_country")
  val originCountry: String
)

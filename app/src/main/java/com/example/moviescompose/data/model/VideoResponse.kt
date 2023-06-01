package com.example.moviescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
  val id: Long? = null,
  val results: List<Video>
)

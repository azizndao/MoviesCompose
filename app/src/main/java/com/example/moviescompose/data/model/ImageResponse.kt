package com.example.moviescompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val id: Long? = null,
    val backdrops: List<Image>,
    val logos: List<Image>,
    val posters: List<Image>
)

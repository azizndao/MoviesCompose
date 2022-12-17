package com.example.moviescompose.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Creator(
    val id: Int,
    @SerialName("credit_id")
    val creditId: String,
    val name: String,
    val gender: Int,
    @SerialName("profile_path")
    val profilePath: String? = null,
)

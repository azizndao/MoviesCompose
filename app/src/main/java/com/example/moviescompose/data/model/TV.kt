package com.example.moviescompose.data.model


import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*



@Serializable
data class TV(
    val id: Long,
    val name: String,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>,

    @SerialName("origin_country")
    val originCountry: List<String>,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_name")
    val originalName: String,

    val overview: String,
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Long,

    val adult: Boolean = false
)
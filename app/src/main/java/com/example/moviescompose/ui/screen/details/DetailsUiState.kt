package com.example.moviescompose.ui.screen.details

import com.example.moviescompose.data.model.CardUiState
import com.example.moviescompose.data.model.ImageResponse
import com.example.moviescompose.data.model.MovieDetails
import com.example.moviescompose.data.model.TVDetails
import com.example.moviescompose.data.model.Video

sealed class DetailsUiState {

    object Loading : DetailsUiState()

    data class Error(val exception: Exception) : DetailsUiState()

    data class Success(
        val id: Long,
        val title: String,
        val overview: String?,
        val backdropPath: String?,
        val posterPath: String?,
        val voteCount: Long,
        val voteAverage: Double,
        val date: String?,
        val images: ImageResponse,
        val videos: List<Video>,
        val similarItems: List<CardUiState>
    ) : DetailsUiState()
}


fun MovieDetails.toUiState(similarItems: List<CardUiState>) = DetailsUiState.Success(
    id = id,
    title = title,
    overview = overview,
    backdropPath = backdropPath,
    posterPath = posterPath,
    voteCount = voteCount,
    voteAverage = voteAverage,
    date = releaseDate,
    images = images,
    videos = videos.results,
    similarItems = similarItems
)

fun TVDetails.toUiState(similarItems: List<CardUiState>) = DetailsUiState.Success(
    id = id,
    title = name,
    overview = overview,
    backdropPath = backdropPath,
    posterPath = posterPath,
    voteCount = voteCount,
    voteAverage = voteAverage,
    date = firstAirDate,
    images = images,
    videos = videos.results,
    similarItems = similarItems,
)
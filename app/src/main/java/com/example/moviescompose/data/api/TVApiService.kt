package com.example.moviescompose.data.api

import com.example.moviescompose.data.model.TV
import com.example.moviescompose.data.model.TVDetails
import com.example.moviescompose.data.model.TheMovieResponse
import com.example.moviescompose.data.model.Video

interface TVApiService {

    suspend fun getPopular(page: Int, language: String = "en-US"): TheMovieResponse<TV>

    suspend fun getTopRated(page: Int, language: String = "en-US"): TheMovieResponse<TV>

    suspend fun getOnTv(page: Int, language: String = "en-US"): TheMovieResponse<TV>

    suspend fun getAiringToday(page: Int, language: String = "en-US"): TheMovieResponse<TV>

    suspend fun searchMovie(
        query: String,
        page: Int,
        language: String = "en-US"
    ): TheMovieResponse<TV>

    suspend fun getTvDetails(id: Long, language: String = "en-US"): TVDetails

    suspend fun getTvVideos(id: Long, language: String = "en-US"): List<Video>

    suspend fun getSimilarTvs(id: Long, page: Int, language: String = "en-US"): TheMovieResponse<TV>
}

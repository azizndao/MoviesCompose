package com.example.moviescompose.data.api

import com.example.moviescompose.BuildConfig
import com.example.moviescompose.data.model.TV
import com.example.moviescompose.data.model.TVDetails
import com.example.moviescompose.data.model.TheMovieResponse
import com.example.moviescompose.data.model.Video
import com.example.moviescompose.data.model.VideoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TVApiServiceImpl(
    private val httpClient: HttpClient
) : TVApiService {
    private val baseUrl = BuildConfig.BACKEND_URL
    private val apiKey = BuildConfig.API_KEY

    override suspend fun getPopular(page: Int, language: String): TheMovieResponse<TV> {
        return httpClient.get("$baseUrl/tv/popular") {
            parameter("api_key", apiKey)
            parameter("language", language)
            parameter("page", page)
        }.body()
    }

    override suspend fun getTopRated(page: Int, language: String): TheMovieResponse<TV> {
        return httpClient.get("$baseUrl/tv/top_rated") {
            parameter("api_key", apiKey)
            parameter("language", language)
            parameter("page", page)
        }.body()
    }

    override suspend fun getOnTv(page: Int, language: String): TheMovieResponse<TV> {
        return httpClient.get("$baseUrl/tv/on_the_air") {
            parameter("api_key", apiKey)
            parameter("language", language)
            parameter("page", page)
        }.body()
    }

    override suspend fun getAiringToday(page: Int, language: String): TheMovieResponse<TV> {
        return httpClient.get("$baseUrl/tv/airing_today") {
            parameter("api_key", apiKey)
            parameter("language", language)
            parameter("page", page)
        }.body()
    }

    override suspend fun searchMovie(
        query: String,
        page: Int,
        language: String
    ): TheMovieResponse<TV> {
        val response = httpClient.get("$baseUrl/search/tv") {
            parameter("api_key", apiKey)
            parameter("language", language)
            parameter("query", query)
            parameter("page", page)
        }
        return response.body()
    }

    override suspend fun getTvDetails(id: Long, language: String): TVDetails {
        return httpClient.get("$baseUrl/tv/$id") {
            parameter("api_key", apiKey)
            parameter("language", language)
            parameter("append_to_response", "images,videos")
            parameter("include_image_language", language.split('-').firstOrNull())
        }.body()
    }

    override suspend fun getTvVideos(id: Long, language: String): List<Video> {
        return httpClient.get("$baseUrl/tv/$id/videos") {
            parameter("api_key", apiKey)
            parameter("language", language)
        }.body<VideoResponse>().results
    }

    override suspend fun getSimilarTvs(
        id: Long,
        page: Int,
        language: String
    ): TheMovieResponse<TV> {
        return httpClient.get("$baseUrl/tv/$id/similar") {
            parameter("api_key", apiKey)
            parameter("language", language)
            parameter("page", page)
        }.body()
    }
}
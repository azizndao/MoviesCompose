package com.example.moviescompose.data.api

import com.example.moviescompose.BuildConfig
import com.example.moviescompose.data.model.TV
import com.example.moviescompose.data.model.TVDetails
import com.example.moviescompose.data.model.TheMovieResponse
import com.example.moviescompose.data.model.Video
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVApiService {

  @GET("tv/popular")
  suspend fun getPopular(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TV>

  @GET("tv/top_rated")
  suspend fun getTopRated(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TV>

  @GET("tv/on_the_air")
  suspend fun getOnTv(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TV>

  @GET("tv/airing_today")
  suspend fun getAiringToday(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TV>

  @GET("search/tv")
  suspend fun searchMovie(
    query: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TV>

  @GET("tv/{id}")
  suspend fun getTvDetails(
    @Path("id") id: Long,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TVDetails

  @GET("tv/{id}/videos")
  suspend fun getTvVideos(
    @Path("id") id: Long,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): List<Video>

  @GET("tv/{id}/similar")
  suspend fun getSimilarTvs(
    @Path("id") id: Long,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TV>
}

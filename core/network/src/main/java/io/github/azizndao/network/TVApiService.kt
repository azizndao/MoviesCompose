package io.github.azizndao.network

import io.github.azizndao.model.TVDetails
import io.github.azizndao.model.TVShow
import io.github.azizndao.model.Video
import io.github.azizndao.network.model.TheMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVApiService {

  @GET("tv/popular")
  suspend fun getPopular(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TVShow>

  @GET("tv/top_rated")
  suspend fun getTopRated(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TVShow>

  @GET("tv/on_the_air")
  suspend fun getOnTv(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TVShow>

  @GET("tv/airing_today")
  suspend fun getAiringToday(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TVShow>

  @GET("search/tv")
  suspend fun searchMovie(
    query: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<TVShow>

  @GET("tv/{id}")
  suspend fun getTvDetails(
    @Path("id") id: Long,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    @Query("append_to_response") appendToResponse: String = "videos,images"
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
  ): TheMovieResponse<TVShow>
}

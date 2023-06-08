package io.github.azizndao.network

import io.github.azizndao.model.Genre
import io.github.azizndao.model.Movie
import io.github.azizndao.model.MovieDetails
import io.github.azizndao.model.Video
import io.github.azizndao.network.model.TheMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

  @GET("discover/movie")
  suspend fun getMoviesList(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    @Query("with_watch_monetization_types") monetizationTypes: String = "flatrate"
  ): TheMovieResponse<Movie>

  @GET("movie/popular")
  suspend fun getPopular(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<Movie>

  @GET("movie/top_rated")
  suspend fun getTopRated(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<Movie>

  @GET("movie/upcoming")
  suspend fun getUpcoming(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<Movie>

  @GET("movie/now_playing")
  suspend fun getNowPlaying(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<Movie>

  suspend fun getLatest(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<Movie>

  @GET("search/movie")
  suspend fun searchMovie(
    query: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<Movie>

  @GET("movie/{id}")
  suspend fun getMovieDetails(
    @Path("id") id: Long,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    @Query("append_to_response") appendToResponse: String = "videos,images"
  ): MovieDetails

  @GET("movie/{id}/similar")
  suspend fun getSimilarMovies(
    @Path("id") id: Long,
    @Query("page") page: Int = 1,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): TheMovieResponse<Movie>

  @GET("genre/movie/list")
  suspend fun getAllGenres(
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): List<Genre>

  @GET("movie/{id}/videos")
  suspend fun getMovieVideos(
    @Query("id") movieId: Long,
    @Query("language") language: String = "en-US",
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
  ): List<Video>
}

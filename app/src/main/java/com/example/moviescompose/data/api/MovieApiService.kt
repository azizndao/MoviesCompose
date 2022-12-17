package com.example.moviescompose.data.api

import com.example.moviescompose.data.model.Genre
import com.example.moviescompose.data.model.Movie
import com.example.moviescompose.data.model.MovieDetails
import com.example.moviescompose.data.model.SortKey
import com.example.moviescompose.data.model.TheMovieResponse
import com.example.moviescompose.data.model.Video

interface MovieApiService {

    suspend fun getMoviesList(
        page: Int,
        sortKey: String = SortKey.POPULARITY.key,
        language: String = "en-US",
        ascendant: Boolean = true
    ): TheMovieResponse<Movie>

    suspend fun getPopular(page: Int, language: String = "en-US"): TheMovieResponse<Movie>

    suspend fun getTopRated(page: Int, language: String = "en-US"): TheMovieResponse<Movie>

    suspend fun getUpcoming(page: Int, language: String = "en-US"): TheMovieResponse<Movie>

    suspend fun getNowPlaying(page: Int, language: String = "en-US"): TheMovieResponse<Movie>

    suspend fun getLatest(page: Int, language: String = "en-US"): TheMovieResponse<Movie>

    suspend fun searchMovie(
        query: String,
        page: Int,
        language: String = "en-US"
    ): TheMovieResponse<Movie>

    suspend fun getMovieDetails(id: Long, language: String = "en-US"): MovieDetails

    suspend fun getSimilarMovies(id: Long, page: Int, language: String = "en-US"): TheMovieResponse<Movie>

    suspend fun getAllGenres(language: String = "en-US"): List<Genre>

    suspend fun getMovieVideos(movieId: Long, language: String = "en-US"): List<Video>
}

package com.example.moviescompose.data

import com.example.moviescompose.BuildConfig
import com.example.moviescompose.data.api.MovieApiService
import com.example.moviescompose.data.api.TVApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val RepoModule = module {
  factory { get<Json>().asConverterFactory(MediaType.get("application/json")) }

  factory {
    Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(get())
      .build()
      .create<MovieApiService>()
  }

  factory {
    Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(get())
      .build()
      .create<TVApiService>()
  }
}
package io.github.azizndao.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val NetworkModule = module {

  single {
    Json {
      prettyPrint = true
      ignoreUnknownKeys = true
    }
  }

  factory {
    Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(get<Json>().asConverterFactory(MediaType.get("application/json")))
      .build()
  }

  factory { get<Retrofit>().create<MovieApiService>() }

  factory { get<Retrofit>().create<TVApiService>() }
}
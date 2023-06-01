package com.example.moviescompose.utils

import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UtilsModule = module {

  singleOf(::UserPreferences)

  single {
    Json {
      prettyPrint = true
      ignoreUnknownKeys = true
    }
  }
}
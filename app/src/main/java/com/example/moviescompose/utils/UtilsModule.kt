package com.example.moviescompose.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UtilsModule = module {

    singleOf(::UserPreferences)

    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) { json() }
        }
    }
}
package com.example.moviescompose.data

import com.example.moviescompose.data.api.MovieApiService
import com.example.moviescompose.data.api.MovieApiServiceImpl
import com.example.moviescompose.data.api.TVApiService
import com.example.moviescompose.data.api.TVApiServiceImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val RepoModule = module {
    factoryOf(::MovieApiServiceImpl) bind MovieApiService::class
    factoryOf(::TVApiServiceImpl) bind TVApiService::class
}
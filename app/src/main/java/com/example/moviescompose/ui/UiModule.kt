package com.example.moviescompose.ui

import com.example.moviescompose.ui.screen.details.DetailsViewModel
import com.example.moviescompose.ui.screen.home.HomeViewModel
import com.example.moviescompose.ui.screen.home.sort.SortViewModel
import com.example.moviescompose.ui.screen.movie.MovieViewModel
import com.example.moviescompose.ui.screen.tv.TVViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val UiModule = module {
  viewModelOf(::HomeViewModel)
  viewModelOf(::MovieViewModel)
  viewModelOf(::TVViewModel)
  viewModelOf(::DetailsViewModel)
  viewModelOf(::SortViewModel)
}
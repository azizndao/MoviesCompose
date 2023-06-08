package io.github.azizndao.trailer.ui

import io.github.azizndao.trailer.ui.screen.details.DetailsViewModel
import io.github.azizndao.trailer.ui.screen.home.HomeViewModel
import io.github.azizndao.trailer.ui.screen.home.sort.SortViewModel
import io.github.azizndao.trailer.ui.screen.movie.MovieViewModel
import io.github.azizndao.trailer.ui.screen.tv.TVViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val UiModule = module {
  viewModelOf(::HomeViewModel)
  viewModelOf(::MovieViewModel)
  viewModelOf(::TVViewModel)
  viewModelOf(::DetailsViewModel)
  viewModelOf(::SortViewModel)
}
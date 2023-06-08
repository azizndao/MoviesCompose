package io.github.azizndao.trailer.ui.screen.home.sort

data class MovieSortUiState(
  val key: String,
  val label: String,
  val ascendant: Boolean = true,
  val selected: Boolean,
)
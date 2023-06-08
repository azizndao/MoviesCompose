package io.github.azizndao.trailer.ui.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val SEARCH_ROUTE = "search"

fun NavHostController.navigateToSearch() = navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchDestination(
  showItem: (Long, String) -> Unit,
  contentPadding: PaddingValues
) {
  composable(SEARCH_ROUTE) {
    SearchScreen(showItem = showItem, contentPadding = contentPadding)
  }
}

@Composable
private fun SearchScreen(
  contentPadding: PaddingValues = PaddingValues(),
  showItem: (id: Long, type: String) -> Unit
) {
  LazyColumn(contentPadding = contentPadding) {}
}

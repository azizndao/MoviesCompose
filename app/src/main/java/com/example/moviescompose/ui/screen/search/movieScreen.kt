package com.example.moviescompose.ui.screen.search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val SEARCH_ROUTE = "search"

fun NavHostController.navigateToSearch() = navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchDestination(showItem: (Long, String) -> Unit) {
    composable(SEARCH_ROUTE) {
        SearchScreen(showItem)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(showItem: (id: Long, type: String) -> Unit) {
    Scaffold { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {}
    }
}
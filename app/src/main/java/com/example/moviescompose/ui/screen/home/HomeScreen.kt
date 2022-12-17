package com.example.moviescompose.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.moviescompose.R
import com.example.moviescompose.data.model.CardUiState
import com.example.moviescompose.data.model.ITEM_TYPE_MOVIE
import com.example.moviescompose.data.model.ITEM_TYPE_TV_SHOW
import com.example.moviescompose.ui.views.HeadlineMovieItem
import com.example.moviescompose.ui.views.MovieItem
import com.example.moviescompose.utils.UiState
import org.koin.androidx.compose.getViewModel

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeDestination(showItem: (Long, String) -> Unit) {
    composable(HOME_ROUTE) {
        HomeScreen(showItem = showItem)
    }
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    showItem: (Long, String) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        when (viewModel.uiState) {
            UiState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 70.dp)
                    .wrapContentSize()
            )

            is UiState.Error -> Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp, start = 24.dp, end = 24.dp)
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val exception = (viewModel.uiState as UiState.Error).exception!!

                Text(
                    text = exception.stackTraceToString(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Button(onClick = viewModel::refresh) {
                    Text(stringResource(id = R.string.retry))
                }
            }

            is UiState.Success -> {
                val uiState = (viewModel.uiState as UiState.Success<HomeUiState>).data
                val movie = uiState.popularMovie
                HeadlineMovieItem(movie = uiState.popularMovie) {
                    showItem(movie.id, ITEM_TYPE_MOVIE)
                }

                MoviesRow(
                    R.string.movie,
                    uiState.moviePagingItems.collectAsLazyPagingItems(),
                ) { movieId -> showItem(movieId, ITEM_TYPE_MOVIE) }

                MoviesRow(
                    R.string.tv,
                    uiState.tvPagingItems.collectAsLazyPagingItems()
                ) { movieId ->
                    showItem(movieId, ITEM_TYPE_TV_SHOW)
                }
            }
        }
    }
}


@Composable
fun MoviesRow(
    @StringRes titleId: Int,
    pagingItems: LazyPagingItems<CardUiState>,
    onItemClick: (Long) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(id = titleId),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.height(332.dp)
        ) {
            items(pagingItems) { movie ->
                MovieItem(
                    uiState = movie!!,
                    onClick = { onItemClick(movie.id) },
                    modifier = Modifier.width(175.dp)
                )
            }
        }
    }
}




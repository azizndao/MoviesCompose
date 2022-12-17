package com.example.moviescompose.ui.screen.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviescompose.R
import com.example.moviescompose.ui.views.MovieItem
import com.example.moviescompose.ui.views.loadingStateItem
import com.example.moviescompose.utils.items
import com.example.moviescompose.utils.pagerTabIndicatorOffset
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.compose.getViewModel

const val MOVIES_ROUTE = "movies"

fun NavHostController.navigateToMovies() = navigate(MOVIES_ROUTE)

fun NavGraphBuilder.moviesDestination(onNavigate: (Long) -> Unit) {
    composable(MOVIES_ROUTE) {
        MovieScreen(onNavigate = onNavigate)
    }
}

private val tabs =
    listOf(R.string.popular, R.string.now_playing, R.string.upcoming, R.string.top_rated)

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)
@Composable
private fun MovieScreen(viewModel: MovieViewModel = getViewModel(), onNavigate: (Long) -> Unit) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(stringResource(R.string.movies)) },
        )

        val pagerState = rememberPagerState()

        ScrollableTabRow(
            selectedTabIndex = viewModel.selectedTabIndex,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
            }
        ) {
            tabs.forEachIndexed { index, labelId ->
                Tab(
                    selected = viewModel.selectedTabIndex == index,
                    onClick = { viewModel.selectTab(index) },
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                ) {
                    Text(
                        stringResource(labelId),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }


        LaunchedEffect(key1 = viewModel.selectedTabIndex) {
            pagerState.animateScrollToPage(viewModel.selectedTabIndex)
        }


        HorizontalPager(count = tabs.size, state = pagerState) { index ->
            val pagingItems = remember { viewModel.tabViews[tabs[index]] }!!
                .collectAsLazyPagingItems()

            val gridState = rememberLazyGridState()

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(133.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
            ) {

                loadingStateItem(pagingItems.loadState.refresh, onRetry = pagingItems::retry)

                items(pagingItems, key = { it.id }) { uiState ->
                    if (uiState == null) {
                        Box(
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    } else {
                        MovieItem(
                            uiState = uiState,
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxSize(),
                            onClick = { onNavigate(uiState.id) }
                        )
                    }
                }

                loadingStateItem(pagingItems.loadState.append, onRetry = pagingItems::retry)
            }
        }
    }
}

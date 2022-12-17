package com.example.moviescompose.ui.screen.tv

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import org.koin.androidx.compose.getViewModel

const val TV_SHOWS_ROUTE = "tv"

//fun NavHostController.navigateToTShows() = navigate(TV_SHOWS_ROUTE)

fun NavGraphBuilder.tvShowsDestination(onNavigate: (Long) -> Unit) {
    composable(TV_SHOWS_ROUTE) {
        TVScreen(onNavigate = onNavigate)
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
)
@Composable
private fun TVScreen(viewModel: TVViewModel = getViewModel(), onNavigate: (Long) -> Unit) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(stringResource(R.string.tv)) },
        )

        ScrollableTabRow(
            selectedTabIndex = viewModel.selectedTabIndex,
            edgePadding = 0.dp) {
            viewModel.tabs.forEachIndexed { index, labelId ->
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

        val pagingItems = viewModel.selectedTabData.collectAsLazyPagingItems()

        AnimatedContent(targetState = viewModel.selectedTabIndex,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
            transitionSpec = {
                val enterDirection = if (initialState < targetState) {
                    AnimatedContentScope.SlideDirection.Start
                } else {
                    AnimatedContentScope.SlideDirection.End
                }

                val exitDirection = if (initialState > targetState) {
                    AnimatedContentScope.SlideDirection.Start
                } else {
                    AnimatedContentScope.SlideDirection.End
                }

                slideIntoContainer(enterDirection) + fadeIn() with slideOutOfContainer(exitDirection) + fadeOut()
            }) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                loadingStateItem(pagingItems.loadState.refresh, onRetry = pagingItems::retry)

                items(pagingItems) { uiState ->
                    MovieItem(uiState = uiState!!,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxSize(),
                        onClick = { onNavigate(uiState.id) })
                }

                loadingStateItem(pagingItems.loadState.append, onRetry = pagingItems::retry)
            }
        }
    }
}

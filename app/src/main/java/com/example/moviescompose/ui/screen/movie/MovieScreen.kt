package com.example.moviescompose.ui.screen.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.moviescompose.R
import com.example.moviescompose.ui.views.MovieItem
import com.example.moviescompose.ui.views.loadingStateItem
import com.example.moviescompose.utils.pagerTabIndicatorOffset
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
  ExperimentalFoundationApi::class
)
@Composable
private fun MovieScreen(viewModel: MovieViewModel = getViewModel(), onNavigate: (Long) -> Unit) {

  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

  Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
    TopAppBar(
      scrollBehavior = scrollBehavior,
      title = { Text(stringResource(R.string.movies)) },
      actions = {
        IconButton(onClick = { /*TODO*/ }) {
          Icon(painterResource(id = R.drawable.ic_search), null)
        }
      }
    )

    val pagerState = rememberPagerState { tabs.size }

    ScrollableTabRow(
      selectedTabIndex = viewModel.selectedTabIndex,
      edgePadding = 0.dp,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
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

    HorizontalPager(state = pagerState) { index ->
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

        items(
          pagingItems.itemCount,
          key = pagingItems.itemKey(),
          contentType = pagingItems.itemContentType()
        ) { index ->

          val uiState = pagingItems[index]!!

          MovieItem(
            uiState = uiState,
            modifier = Modifier
              .height(300.dp)
              .fillMaxSize(),
            onClick = { onNavigate(uiState.id) }
          )
        }

        loadingStateItem(pagingItems.loadState.append, onRetry = pagingItems::retry)
      }
    }
  }
}

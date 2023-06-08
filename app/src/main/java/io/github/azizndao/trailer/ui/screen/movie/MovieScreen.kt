package io.github.azizndao.trailer.ui.screen.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.github.azizndao.trailer.R
import io.github.azizndao.trailer.data.model.CardUiState
import io.github.azizndao.trailer.ui.theme.add
import io.github.azizndao.trailer.ui.views.MovieItem
import io.github.azizndao.trailer.ui.views.loadingStateItem
import org.koin.androidx.compose.getViewModel

const val MOVIES_ROUTE = "movies"

fun NavHostController.navigateToMovies() = navigate(MOVIES_ROUTE)

fun NavGraphBuilder.moviesDestination(contentPadding: PaddingValues, onNavigate: (Long) -> Unit) {
  composable(MOVIES_ROUTE) {
    MovieScreen(onNavigate = onNavigate, contentPadding = contentPadding)
  }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun MovieScreen(
  viewModel: MovieViewModel = getViewModel(),
  contentPadding: PaddingValues,
  onNavigate: (Long) -> Unit
) {

  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .nestedScroll(scrollBehavior.nestedScrollConnection)
  ) {
    TopAppBar(
      scrollBehavior = scrollBehavior,
      title = { Text(stringResource(R.string.movies)) },
      actions = {
        IconButton(onClick = { /*TODO*/ }) {
          Icon(painterResource(id = R.drawable.ic_search), null)
        }
      }
    )

    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      itemsIndexed(viewModel.tabs) { index, labelId ->
        FilterChip(
          selected = viewModel.selectedTabIndex == index,
          onClick = { viewModel.selectTab(index) },
          label = { Text(stringResource(labelId)) },
          shape = CircleShape
        )
      }
    }

    val pagerState = rememberPagerState { viewModel.tabs.size }

    LaunchedEffect(viewModel.selectedTabIndex) {
      pagerState.animateScrollToPage(viewModel.selectedTabIndex)
    }

    HorizontalPager(state = pagerState) { index ->
      TabViewItem(
        pagingItems = remember { viewModel.getPageItems(index) }.collectAsLazyPagingItems(),
        onNavigate = onNavigate,
        contentPadding = contentPadding,
      )
    }
  }
}

@Composable
private fun TabViewItem(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
  gridState: LazyGridState = rememberLazyGridState(),
  pagingItems: LazyPagingItems<CardUiState>,
  onNavigate: (Long) -> Unit
) {

  LazyVerticalGrid(
    modifier = modifier,
    state = gridState,
    columns = GridCells.Adaptive(133.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = contentPadding.add(horizontal = 16.dp, vertical = 8.dp),
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
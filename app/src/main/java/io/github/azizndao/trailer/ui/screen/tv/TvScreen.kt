package io.github.azizndao.trailer.ui.screen.tv

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

const val TV_SHOWS_ROUTE = "tv"

//fun NavHostController.navigateToTShows() = navigate(TV_SHOWS_ROUTE)

fun NavGraphBuilder.tvShowsDestination(contentPadding: PaddingValues, onNavigate: (Long) -> Unit) {
  composable(TV_SHOWS_ROUTE) {
    TVScreen(onNavigate = onNavigate, contentPadding = contentPadding)
  }
}

val tabs = listOf(R.string.popular, R.string.aring_today, R.string.on_tv, R.string.top_rated)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun TVScreen(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
  viewModel: TVViewModel = getViewModel(),
  onNavigate: (Long) -> Unit
) {

  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

  Column(
    modifier = modifier
      .fillMaxSize()
      .nestedScroll(scrollBehavior.nestedScrollConnection)
  ) {
    TopAppBar(
      scrollBehavior = scrollBehavior,
      title = { Text(stringResource(R.string.tv)) },
    )

    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      itemsIndexed(tabs) { index, labelId ->
        FilterChip(
          selected = viewModel.selectedTabIndex == index,
          onClick = { viewModel.selectTab(index) },
          label = { Text(stringResource(labelId)) },
          shape = CircleShape
        )
      }
    }

    val pagerState = rememberPagerState { tabs.size }
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
    modifier = modifier.fillMaxSize(),
    state = gridState,
    columns = GridCells.Fixed(2),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = contentPadding.add(16.dp),
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

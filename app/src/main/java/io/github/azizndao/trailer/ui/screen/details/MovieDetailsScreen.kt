package io.github.azizndao.trailer.ui.screen.details

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.github.azizndao.model.Image
import io.github.azizndao.model.ItemType
import io.github.azizndao.model.Video
import io.github.azizndao.trailer.R
import io.github.azizndao.trailer.data.model.CardUiState
import io.github.azizndao.trailer.ui.theme.MoviesComposeTheme
import io.github.azizndao.trailer.ui.views.MovieItem
import io.github.azizndao.trailer.ui.views.NetworkImage
import io.github.azizndao.trailer.ui.views.YouTubePlayerItem
import io.github.azizndao.network.ImageHelper
import io.github.azizndao.trailer.utils.preview.DetailsPreviewProvider
import io.github.azizndao.trailer.utils.preview.ListBackdropImagePreviewProvider
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

const val PARAM_TYPE = "type"
const val PARAM_MOVIE_ID = "movieId"
const val MOVIES_DETAILS = "details/{type}/{movieId}"

fun NavHostController.navigateToDetails(movieId: Long, @ItemType type: String) = navigate(
  "details/$type/$movieId"
)

fun NavGraphBuilder.detailsDestination(
  showSimilar: (Long, String) -> Unit,
  onBackPress: () -> Unit
) {
  composable(
    MOVIES_DETAILS,
    arguments = listOf(
      navArgument("movieId") { type = NavType.LongType },
      navArgument("type") { type = NavType.StringType },
    )
  ) { backEntry ->

    val movieId = backEntry.arguments!!.getLong(PARAM_MOVIE_ID)
    val itemType = backEntry.arguments!!.getString(PARAM_TYPE)


    val viewModel = getViewModel<DetailsViewModel> { parametersOf(movieId, itemType) }

    MovieDetailScreen(
      uiState = viewModel.detailsUiState,
      onBackPress = onBackPress,
      showSimilar = { showSimilar(it, viewModel.type) }
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
  uiState: DetailsUiState,
  onBackPress: () -> Unit,
  showSimilar: (Long) -> Unit,
) {

  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .nestedScroll(scrollBehavior.nestedScrollConnection),
    verticalArrangement = Arrangement.spacedBy(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    contentPadding = WindowInsets.navigationBars.asPaddingValues()
  ) {

    when (uiState) {
      is DetailsUiState.Error -> {
        item {
          Text(uiState.exception.toString())
        }
      }

      DetailsUiState.Loading -> item {
        CircularProgressIndicator(
          modifier = Modifier
            .statusBarsPadding()
            .padding(top = 70.dp)
        )
      }

      is DetailsUiState.Success -> {

        item {
          HeadingItem(uiState, onBackPress = onBackPress)
        }

        item {
          Text(
            text = uiState.title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }

        item {
          Text(
            text = uiState.date ?: stringResource(id = R.string.unknown),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }

        item {
          Text(
            text = uiState.overview ?: stringResource(id = R.string.no_overview),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }

        item {
          Youtube(uiState.videos)
        }

        item {
          Backdrops(uiState.images.backdrops)
        }

        item {
          SimilarItemList(uiState.similarItems) { showSimilar(it) }
        }
      }
    }
  }
}

@Composable
fun DetailSection(
  modifier: Modifier = Modifier,
  @StringRes titleId: Int,
  content: @Composable ColumnScope.() -> Unit
) {

  Column(
    modifier = modifier
      .background(MaterialTheme.colorScheme.background)
      .fillMaxWidth()
      .padding(vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stringResource(id = titleId).capitalize(Locale.current),
      style = MaterialTheme.typography.titleLarge
    )

    content()
  }
}

@Composable
private fun Backdrops(images: List<Image>) {
  DetailSection(titleId = R.string.images) {
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
      items(images) { image ->
        NetworkImage(
          path = image.filePath,
          modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(image.aspectRatio)
        )
      }
    }
  }
}

@Preview
@Composable
fun BackDropPreviews(
  @PreviewParameter(ListBackdropImagePreviewProvider::class) images: List<Image>
) {
  MoviesComposeTheme {
    Backdrops(images = images)
  }
}

@Composable
private fun Youtube(videos: List<Video>) {
  DetailSection(titleId = R.string.videos) {
    Box(
      modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
        .aspectRatio(16f / 9),
      contentAlignment = Alignment.Center
    ) {

      if (videos.isNotEmpty()) {
        YouTubePlayerItem(
          video = videos.first(),
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Preview
@Composable
fun DetailsScreenPreview(
  @PreviewParameter(DetailsPreviewProvider::class) uiState: DetailsUiState
) {
  MoviesComposeTheme {
    Surface {
      MovieDetailScreen(uiState = uiState, onBackPress = { /*TODO*/ }, showSimilar = {})
    }
  }
}


@Composable
private fun SimilarItemList(
  pagingItems: List<CardUiState>,
  modifier: Modifier = Modifier,
  showSimilar: (Long) -> Unit,
) {
  DetailSection(titleId = R.string.similar) {
    LazyRow(
      modifier = modifier
        .height(300.dp)
        .fillMaxWidth(),
      contentPadding = PaddingValues(16.dp),
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

      items(pagingItems) { itemUiState ->
        MovieItem(uiState = itemUiState, modifier = Modifier.width(150.dp)) {
          showSimilar(itemUiState.id)
        }
      }
    }
  }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HeadingItem(details: DetailsUiState.Success, onBackPress: () -> Unit) {
  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    GlideImage(
      details.backdropPath?.let { ImageHelper.getImage(it) },
      contentScale = ContentScale.Crop,
      contentDescription = details.title,
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.22f))
    )

    Column {
      IconButton(
        onClick = onBackPress,
        modifier = Modifier
          .statusBarsPadding()
          .padding(top = 16.dp, start = 8.dp)
      ) {
        Icon(painterResource(id = R.drawable.ic_arrow_back), null)
      }
      NetworkImage(
        details.posterPath?.let { ImageHelper.getImage(it) },
        contentDescription = details.title,
        modifier = Modifier
          .padding(16.dp)
          .height(300.dp)
          .aspectRatio(133f / 200)
      )
    }
  }
}

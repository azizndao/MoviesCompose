package io.github.azizndao.trailer.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.github.azizndao.model.Movie
import io.github.azizndao.trailer.R
import io.github.azizndao.trailer.data.model.CardUiState
import io.github.azizndao.trailer.ui.theme.MoviesComposeTheme
import io.github.azizndao.network.ImageHelper
import io.github.azizndao.trailer.utils.preview.CardUiPreviewProvider

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeadlineMovieItem(movie: Movie, modifier: Modifier = Modifier, onClick: () -> Unit) {
  GlideImage(
    movie.backdropPath?.let { ImageHelper.getImage(500, it) },
    contentScale = ContentScale.Crop,
    contentDescription = movie.title,
    modifier = modifier
      .fillMaxWidth()
      .aspectRatio(1f)
      .clickable(onClick = onClick)
  ) {
    it.placeholder(R.drawable.movie_placeholder)
      .error(R.drawable.movie_placeholder)
  }
}

private const val IMAGE_RATIO = 133f / 200f

@Composable
fun MovieItem(
  modifier: Modifier = Modifier,
  uiState: CardUiState,
  loading: Boolean = false,
  onClick: () -> Unit
) {
  Column(
    modifier = modifier
      .widthIn(133.dp, 200.dp)
      .fillMaxHeight()
      .placeholder(
        visible = loading,
        highlight = PlaceholderHighlight.shimmer(),
      ),
  ) {

    NetworkImage(
      uiState.posterPath,
      modifier = Modifier
        .clip(MaterialTheme.shapes.large)
        .aspectRatio(IMAGE_RATIO)
        .weight(1f)
        .clickable(!loading, onClick = onClick)
    )

    Text(
      uiState.title,
      style = MaterialTheme.typography.labelLarge,
      overflow = TextOverflow.Clip,
      maxLines = 1,
      modifier = Modifier.placeholder(
        visible = loading,
        highlight = PlaceholderHighlight.shimmer(),
      )
    )

    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      RatingBar(modifier = Modifier.height(16.dp), rating = uiState.voteAverage / 2)

      Text(text = "•", modifier = Modifier.padding(horizontal = 4.dp))

      Text(
        uiState.date?.split('-')?.firstOrNull() ?: stringResource(R.string.unknown),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.placeholder(
          visible = loading,
          highlight = PlaceholderHighlight.shimmer(),
        )
      )
    }
  }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCardPreview(
  @PreviewParameter(CardUiPreviewProvider::class) uiState: CardUiState,
) {
  MoviesComposeTheme {
    Surface(modifier = Modifier.padding(16.dp)) {
      MovieItem(uiState = uiState, modifier = Modifier.height(300.dp)) {

      }
    }
  }
}


package com.example.moviescompose.ui.views

import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch
import coil.compose.AsyncImage
import coil.request.SuccessResult
import com.example.moviescompose.R
import com.example.moviescompose.data.model.CardUiState
import com.example.moviescompose.data.model.Movie
import com.example.moviescompose.ui.theme.MoviesComposeTheme
import com.example.moviescompose.utils.ImageHelper
import com.example.moviescompose.utils.preview.CardUiPreviewProvider
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun HeadlineMovieItem(movie: Movie, modifier: Modifier = Modifier, onClick: () -> Unit) {
    AsyncImage(
        movie.backdropPath?.let { ImageHelper.getImage(500, it) },
        placeholder = painterResource(id = R.drawable.movie_placeholder),
        error = painterResource(id = R.drawable.movie_placeholder),
        contentScale = ContentScale.Crop,
        contentDescription = movie.title,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick)
    )
}

fun SuccessResult.loadSwatch(onLoaded: (Swatch?) -> Unit) {
    Palette.from((drawable as BitmapDrawable).bitmap).generate { palette ->
        onLoaded(palette?.dominantSwatch)
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
            .fillMaxHeight(),
    ) {

        NetworkImage(
            uiState.posterPath,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .aspectRatio(IMAGE_RATIO)
                .weight(1f)
                .clickable(!loading, onClick = onClick)
                .placeholder(
                    visible = loading,
                    highlight = PlaceholderHighlight.shimmer(),
                )
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


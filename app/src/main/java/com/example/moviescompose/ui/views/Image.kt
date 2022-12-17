package com.example.moviescompose.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import com.example.moviescompose.R
import com.example.moviescompose.utils.ImageHelper


@Composable
fun NetworkImage(
    path: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    error: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit)? = {
        Image(
            painter = painterResource(id = R.drawable.movie_placeholder),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
) {
    SubcomposeAsyncImage(
        model = path?.let { ImageHelper.getImage(300, it) },
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        loading = {
            CircularProgressIndicator(
                modifier = Modifier
                    .matchParentSize()
                    .wrapContentSize()
            )
        },
        error = error
    )
}
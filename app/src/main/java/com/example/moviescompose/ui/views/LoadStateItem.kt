package com.example.moviescompose.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.moviescompose.R


@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.loadingStateItem(
    loadState: LoadState,
    onRetry: () -> Unit
) {

    when (loadState) {
        is LoadState.Error -> item(span = { GridItemSpan(maxLineSpan) }) {
            ErrorMessage(modifier = Modifier.animateItemPlacement(), loadState, onRetry)
        }

        LoadState.Loading -> item(span = { GridItemSpan(maxLineSpan) }) {
            CircularProgressIndicator(
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .wrapContentSize()
            )
        }

        is LoadState.NotLoading -> {
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.loadingStateItem(
    loadState: LoadState,
    onRetry: () -> Unit
) {

    when (loadState) {
        is LoadState.Error -> item {
            ErrorMessage(modifier = Modifier.animateItemPlacement(), loadState, onRetry)
        }

        LoadState.Loading -> item {
            CircularProgressIndicator(
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .wrapContentSize()
            )
        }

        is LoadState.NotLoading -> {
        }
    }
}

@Composable
private fun ErrorMessage(
    modifier: Modifier = Modifier,
    loadState: LoadState.Error,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = loadState.error.localizedMessage
                ?: stringResource(id = R.string.unknown),
            textAlign = TextAlign.Center
        )

        Button(onClick = { onRetry() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

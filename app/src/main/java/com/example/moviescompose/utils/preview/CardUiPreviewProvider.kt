package com.example.moviescompose.utils.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.moviescompose.data.model.CardUiState

class CardUiPreviewProvider(
  override val values: Sequence<CardUiState> = sequenceOf(
    CardUiState(
      id = 436270,
      title = "Black Adam",
      date = "2022-10-19",
      posterPath = "/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
      voteAverage = 7.3,
    ),
    CardUiState(
      id = 724495,
      title = "The Woman King",
      date = "2022-09-15",
      posterPath = "/438QXt1E3WJWb3PqNniK0tAE5c1.jpg",
      voteAverage = 7.9,
    ),
  )
) : PreviewParameterProvider<CardUiState>
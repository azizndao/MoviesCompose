package io.github.azizndao.trailer.utils.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.azizndao.model.ImageList
import io.github.azizndao.trailer.ui.screen.details.DetailsUiState

class DetailsPreviewProvider : PreviewParameterProvider<DetailsUiState> {
  override val values = sequenceOf(
    DetailsUiState.Success(
      id = 436270,
      title = "Black Adam",
      overview = "Près de 5000 ans après avoir été doté des pouvoirs tout puissants des dieux égyptiens – et emprisonné très rapidement après – Black Adam est libéré de sa tombe terrestre, prêt à faire régner sa forme unique de justice dans le monde moderne.",
      backdropPath = "/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
      posterPath = "/hYALH5NPM7xk2XQd2J8wrfmliIW.jpg",
      voteCount = 2621,
      voteAverage = 7.313,
      date = "2022-10-19",
      images = ImageList(
        backdrops = fakeBackdrops,
        logos = listOf(),
        posters = fakeposters,
      ),
      videos = fakeVideos,
      similarItems = listOf(

      )
    )
  )
}
package com.example.moviescompose.utils.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.moviescompose.data.model.Video

class VideoPreviewProvider : PreviewParameterProvider<Video> {
    override val values = fakeVideos.asSequence()
}

class VideoListPreviewProvider : PreviewParameterProvider<List<Video>> {
    override val values = sequenceOf(fakeVideos)
}

val fakeVideos = listOf(
    Video(
        iso639_1 = "fr",
        iso3166_1 = "FR",
        name = "Black Adam - Bande-annonce officielle 2 (VF) - Dwayne Johnson, Pierce Brosnan",
        key = "N1RSz0B7VJw",
        site = "YouTube",
        size = 1080,
        type = "Trailer",
        official = true,
        publishedAt = "2022-09-09T00:00:35.000Z",
        id = "631a9815a5743d008419eedd"
    ), Video(
        iso639_1 = "fr",
        iso3166_1 = "FR",
        name = "Black Adam - Comic-Con Sneak Peek (VF) - Dwayne Johnson, Pierce Brosnan",
        key = "UVzWKYa4_-0",
        site = "YouTube",
        size = 1080,
        type = "Trailer",
        official = true,
        publishedAt = "2022-07-23T18:20:46.000Z",
        id = "62dc5ec1ea84c7004fc613a1"
    ), Video(
        iso639_1 = "fr",
        iso3166_1 = "FR",
        name = "Black Adam – Bande annonce officielle 1 (VF)",
        key = "qNqJkymV2Jk",
        site = "YouTube",
        size = 1080,
        type = "Trailer",
        official = true,
        publishedAt = "2022-06-08T13:00:33.000Z",
        id = "62adee6109c24c0051aff23e"
    )
)
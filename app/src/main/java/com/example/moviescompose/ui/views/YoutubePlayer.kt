package com.example.moviescompose.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.moviescompose.data.model.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerItem(video: Video, modifier: Modifier = Modifier) {

  AndroidView(
    modifier = modifier.fillMaxSize(),
    factory = { context ->
      YouTubePlayerView(context).apply {
        findViewTreeLifecycleOwner()?.lifecycle?.addObserver(this)
        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
          override fun onReady(youTubePlayer: YouTubePlayer) {
            youTubePlayer.cueVideo(video.key, 0f)
          }
        })
      }
    }
  )
}
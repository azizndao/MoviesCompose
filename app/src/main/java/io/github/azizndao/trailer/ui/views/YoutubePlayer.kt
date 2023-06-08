package io.github.azizndao.trailer.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import io.github.azizndao.model.Video

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
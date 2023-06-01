package com.example.moviescompose.utils

import com.example.moviescompose.BuildConfig.IMAGE_BASE_URL

object ImageHelper {

  fun getImage(width: Int, path: String): String {
    return "$IMAGE_BASE_URL/w$width$path"
  }

  fun getImage(path: String): String {
    return "$IMAGE_BASE_URL/original$path"
  }
}
package io.github.azizndao.network

import io.github.azizndao.network.BuildConfig.IMAGE_BASE_URL

object ImageHelper {

  fun getImage(width: Int, path: String): String = "$IMAGE_BASE_URL/w$width$path"

  fun getImage(path: String): String = "$IMAGE_BASE_URL/original$path"
}
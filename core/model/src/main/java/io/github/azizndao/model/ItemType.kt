package io.github.azizndao.model

import androidx.annotation.StringDef

@Retention(AnnotationRetention.BINARY)
@StringDef(ITEM_TYPE_MOVIE, ITEM_TYPE_TV_SHOW)
annotation class ItemType

const val ITEM_TYPE_MOVIE = "tv"
const val ITEM_TYPE_TV_SHOW = "movie"


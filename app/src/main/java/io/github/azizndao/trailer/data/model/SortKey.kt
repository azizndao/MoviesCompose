package io.github.azizndao.trailer.data.model

import androidx.annotation.StringRes
import io.github.azizndao.trailer.R

enum class SortKey(val key: String, @StringRes val labelId: Int) {
  ORIGINAL_TITLE("original_title", R.string.sort_title),
  POPULARITY("popularity", R.string.sort_popularity),
  RELEASE_DATE("release_date", R.string.sort_release_date),
  PRIMARY_RELEASE_DATE("primary_release_date", R.string.sort_primary_release_date),
  REVENUE("revenue", R.string.sort_revenue),
  VOTE_AVERAGE("vote_average", R.string.sort_vote_average),
  VOTE_COUNT("vote_count", R.string.sort_vote_count),
}
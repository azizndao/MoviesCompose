package io.github.azizndao.trailer.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun PaddingValues.add(all: Dp): PaddingValues {
  val direction = LocalLayoutDirection.current
  return PaddingValues(
    start = all + calculateStartPadding(direction),
    end = all + calculateEndPadding(direction),
    top = all + calculateTopPadding(),
    bottom = all + calculateBottomPadding()
  )
}

@Composable
fun PaddingValues.add(horizontal: Dp = 0.dp, vertical: Dp = 0.dp): PaddingValues {
  val direction = LocalLayoutDirection.current
  return PaddingValues(
    start = horizontal + calculateStartPadding(direction),
    end = horizontal + calculateEndPadding(direction),
    top = vertical + calculateTopPadding(),
    bottom = vertical + calculateBottomPadding()
  )
}


@Composable
fun PaddingValues.add(
  start: Dp = 0.dp,
  end: Dp = 0.dp,
  top: Dp = 0.dp,
  bottom: Dp = 0.dp
): PaddingValues {
  val direction = LocalLayoutDirection.current
  return PaddingValues(
    start = start + calculateStartPadding(direction),
    end = end + calculateEndPadding(direction),
    top = top + calculateTopPadding(),
    bottom = bottom + calculateBottomPadding()
  )
}

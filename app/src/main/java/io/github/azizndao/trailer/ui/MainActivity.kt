package io.github.azizndao.trailer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.azizndao.model.ITEM_TYPE_MOVIE
import io.github.azizndao.model.ITEM_TYPE_TV_SHOW
import io.github.azizndao.trailer.R
import io.github.azizndao.trailer.ui.screen.details.detailsDestination
import io.github.azizndao.trailer.ui.screen.details.navigateToDetails
import io.github.azizndao.trailer.ui.screen.home.HOME_ROUTE
import io.github.azizndao.trailer.ui.screen.home.homeDestination
import io.github.azizndao.trailer.ui.screen.movie.MOVIES_ROUTE
import io.github.azizndao.trailer.ui.screen.movie.moviesDestination
import io.github.azizndao.trailer.ui.screen.search.SEARCH_ROUTE
import io.github.azizndao.trailer.ui.screen.search.searchDestination
import io.github.azizndao.trailer.ui.screen.tv.TV_SHOWS_ROUTE
import io.github.azizndao.trailer.ui.screen.tv.tvShowsDestination
import io.github.azizndao.trailer.ui.theme.MoviesComposeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setContent {
      MoviesComposeTheme {
        MovieNavGraph()
      }
    }
  }
}


@Composable
private fun MovieNavGraph(
  navController: NavHostController = rememberNavController()
) {

  Scaffold(
    bottomBar = {
      BottomNar(navController)
    },
    contentWindowInsets = WindowInsets(0, 0, 0, 0)
  ) { innerPadding ->
    NavHost(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      navController = navController,
      startDestination = MOVIES_ROUTE
    ) {
      homeDestination(showItem = navController::navigateToDetails, contentPadding = innerPadding)

      searchDestination(navController::navigateToDetails, contentPadding = innerPadding)

      moviesDestination(contentPadding = innerPadding) {
        navController.navigateToDetails(it, ITEM_TYPE_MOVIE)
      }

      detailsDestination(
        showSimilar = navController::navigateToDetails,
        onBackPress = navController::popBackStack
      )

      tvShowsDestination(contentPadding = innerPadding) {
        navController.navigateToDetails(
          it,
          ITEM_TYPE_TV_SHOW
        )
      }
    }
  }
}

@Composable
private fun BottomNar(
  navController: NavHostController
) {
  val currentEntry by navController.currentBackStackEntryAsState()

  val showBottomBar by remember {
    derivedStateOf {
      currentEntry?.destination?.route?.let { currentRoute ->
        navItems.any { it.route == currentRoute }
      } ?: true
    }
  }

  AnimatedVisibility(
    visible = showBottomBar,
    enter = fadeIn() + slideInVertically { it },
    exit = fadeOut() + slideOutVertically { it },
  ) {
    NavigationBar {
      navItems.forEach { item ->
        val selected by remember {
          derivedStateOf {
            currentEntry?.destination?.route?.let { it == item.route } ?: false
          }
        }

        NavigationBarItem(
          selected = selected,
          onClick = {
            navController.navigate(item.route) {
              restoreState = true
              launchSingleTop = true
              popUpTo(navController.graph.startDestinationId) { saveState = true }
            }
          },
          label = { Text(stringResource(id = item.labelId)) },
          icon = {
            Icon(
              painterResource(if (selected) item.selectedIconId else item.iconId),
              null
            )
          }
        )
      }
    }
  }
}


data class NavItem(
  @StringRes val labelId: Int,
  @DrawableRes val selectedIconId: Int,
  @DrawableRes val iconId: Int,
  val route: String,
)

private val navItems = listOf(
  NavItem(
    R.string.movie,
    selectedIconId = R.drawable.ic_movie_filled,
    iconId = R.drawable.ic_movie,
    route = MOVIES_ROUTE
  ),
  NavItem(
    R.string.tv,
    selectedIconId = R.drawable.ic_tv_filled,
    iconId = R.drawable.ic_tv,
    route = TV_SHOWS_ROUTE
  ),
  NavItem(
    R.string.saved,
    selectedIconId = R.drawable.ic_star,
    iconId = R.drawable.ic_star_border,
    route = SEARCH_ROUTE
  ),
  NavItem(
    R.string.account,
    selectedIconId = R.drawable.user_circle_solid,
    iconId = R.drawable.user_circle,
    route = HOME_ROUTE
  ),
)

package com.example.moviescompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.moviescompose.R
import com.example.moviescompose.data.model.ITEM_TYPE_MOVIE
import com.example.moviescompose.data.model.ITEM_TYPE_TV_SHOW
import com.example.moviescompose.ui.screen.details.detailsDestination
import com.example.moviescompose.ui.screen.details.navigateToDetails
import com.example.moviescompose.ui.screen.home.HOME_ROUTE
import com.example.moviescompose.ui.screen.home.homeDestination
import com.example.moviescompose.ui.screen.movie.MOVIES_ROUTE
import com.example.moviescompose.ui.screen.movie.moviesDestination
import com.example.moviescompose.ui.screen.search.SEARCH_ROUTE
import com.example.moviescompose.ui.screen.search.searchDestination
import com.example.moviescompose.ui.screen.tv.TV_SHOWS_ROUTE
import com.example.moviescompose.ui.screen.tv.tvShowsDestination
import com.example.moviescompose.ui.theme.MoviesComposeTheme

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


@OptIn(ExperimentalMaterial3Api::class)
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
      modifier = Modifier.padding(innerPadding),
      navController = navController,
      startDestination = "home"
    ) {
      homeDestination(navController::navigateToDetails)

      searchDestination(navController::navigateToDetails)

      moviesDestination { navController.navigateToDetails(it, ITEM_TYPE_MOVIE) }

      detailsDestination(
        showSimilar = navController::navigateToDetails,
        onBackPress = navController::popBackStack
      )

      tvShowsDestination { navController.navigateToDetails(it, ITEM_TYPE_TV_SHOW) }
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

  AnimatedVisibility(visible = showBottomBar) {
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
    R.string.home,
    selectedIconId = R.drawable.ic_home_filled,
    iconId = R.drawable.ic_home,
    route = HOME_ROUTE
  ),
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
    R.string.search,
    selectedIconId = R.drawable.ic_search,
    iconId = R.drawable.ic_search,
    route = SEARCH_ROUTE
  ),
)

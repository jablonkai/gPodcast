package com.tamas.jablonkai.podcast.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.rounded.Podcasts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tamas.jablonkai.podcast.R
import com.tamas.jablonkai.podcast.ui.screen.podcastlist.PodcastListScreen
import com.tamas.jablonkai.podcast.ui.screen.toptagslist.TopTagListScreen

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object TopPodcasts : Screen("top_podcasts", R.string.top_podcasts, Icons.Rounded.Podcasts)
    object Favorites : Screen("top_tags", R.string.top_tags, Icons.Filled.Tag)
}

@Composable
fun HomeScreen(onPodcastClick: (String) -> Unit) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { AppBar() },
        bottomBar = {
            BottomNavigation {
                listOf(
                    Screen.TopPodcasts,
                    Screen.Favorites,
                ).forEach { screen ->
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.TopPodcasts.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.TopPodcasts.route) {
                PodcastListScreen(
                    hiltViewModel(),
                    onPodcastClick
                )
            }
            composable(Screen.Favorites.route) {
                TopTagListScreen(
                    hiltViewModel(),
                    onPodcastClick
                )
            }
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        }
    )
}

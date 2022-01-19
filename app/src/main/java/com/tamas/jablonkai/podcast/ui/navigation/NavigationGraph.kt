package com.tamas.jablonkai.podcast.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tamas.jablonkai.podcast.ui.navigation.MainArguments.PODCAST_URL
import com.tamas.jablonkai.podcast.ui.screen.home.HomeScreen
import com.tamas.jablonkai.podcast.ui.screen.podcastdetails.PodcastDetailsScreen
import com.tamas.jablonkai.podcast.ui.screen.podcastdetails.PodcastDetailsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object MainDestinations {
    const val HOME_SCREEN = "home_screen"
    const val PODCAST_DETAILS = "podcast_details"
}

object MainArguments {
    const val PODCAST_URL = "podcast_url"
}

@Composable
fun NavigationGraph(
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_SCREEN
) {
    val actions = remember(navController) { NavigationActions(navController) }
    NavHost(navController, startDestination) {
        composable(MainDestinations.HOME_SCREEN) { navBackStackEntry ->
            HomeScreen { url ->
                actions.openPodcastDetails(url, navBackStackEntry)
            }
        }
        composable(MainDestinations.PODCAST_DETAILS + "/{$PODCAST_URL}") { navBackStackEntry ->
            val viewModel = hiltViewModel<PodcastDetailsViewModel>()
            viewModel.loadPodcast(
                navBackStackEntry.arguments?.getString(PODCAST_URL)
            )
            PodcastDetailsScreen(
                viewModel = viewModel,
                actions.upPress
            )
        }
    }
}

class NavigationActions(navController: NavHostController) {
    val openPodcastDetails = { url: String, from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            navController.navigate(MainDestinations.PODCAST_DETAILS + "/$encodedUrl")
        }
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

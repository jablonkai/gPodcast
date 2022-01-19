package com.tamas.jablonkai.podcast.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.tamas.jablonkai.podcast.ui.navigation.NavigationGraph
import com.tamas.jablonkai.podcast.ui.theme.PodcastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PodcastTheme {
                val navController = rememberNavController()
                NavigationGraph(
                    finishActivity = { finish() },
                    navController = navController
                )
            }
        }
    }
}

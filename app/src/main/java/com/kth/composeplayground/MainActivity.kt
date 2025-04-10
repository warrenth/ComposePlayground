package com.kth.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kth.composeplayground.ui.theme.ComposePlaygroundTheme
import com.kth.composeplayground.video.feature.detail.VideoDetailScreen
import com.kth.composeplayground.video.feature.home.VideoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePlaygroundTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        VideoListScreen(
                            onVideoClick = { video ->
                                navController.navigate("detail/${video.id}")
                            }
                        )
                    }
                    composable(
                        route = "detail/{videoId}",
                        arguments = listOf(navArgument("videoId") { type = NavType.StringType })
                    ) {
                        VideoDetailScreen()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePlaygroundTheme {
        VideoListScreen()
    }
}
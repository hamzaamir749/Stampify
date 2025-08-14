package com.coderpakistan.stampify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.coderpakistan.core.presentation.designsystem.StampifyScaffold
import com.coderpakistan.core.presentation.designsystem.StampifyTheme
import com.coderpakistan.core.presentation.designsystem.components.StampifyToolbar


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PlaylistAddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.contentColorFor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            StampifyTheme {
                val navController = rememberNavController()
                val startDestination = Destination.SONGS
                var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

                StampifyScaffold(
                    modifier = Modifier.fillMaxSize(),
                    topAppBar = {
                        StampifyToolbar(
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
                            showBackButton = false,
                            title = "Stampify",
                            titleColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surfaceContainer)
                        )
                    },
                    bottomBar = {
                        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                            Destination.entries.forEachIndexed { index, destination ->
                                NavigationBarItem(
                                    selected = selectedDestination == index,
                                    onClick = {
                                        navController.navigate(route = destination.route)
                                        selectedDestination = index
                                    },
                                    icon = {
                                        Icon(
                                            destination.icon,
                                            contentDescription = destination.contentDescription
                                        )
                                    },
                                    label = { Text(destination.label) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(navController, startDestination, modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
    }
}

@Composable
fun SongsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Songs Screen")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(), // respect Scaffold's padding
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { /* Do something */ }) {
            Text(text = name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StampifyTheme {
        Greeting("Android")
    }
}




@Composable
fun AlbumScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Album Screen")
    }
}

@Composable
fun PlaylistScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Playlist Screen")
    }
}

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    SONGS("songs", "Camera", Icons.Default.CameraEnhance, "Songs"),
    ALBUM("album", "Gallery", Icons.Default.Photo, "Album"),
    PLAYLISTS("playlist", "Settings", Icons.Default.Settings, "Playlist")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.SONGS -> SongsScreen()
                    Destination.ALBUM -> AlbumScreen()
                    Destination.PLAYLISTS -> PlaylistScreen()
                }
            }
        }
    }
}



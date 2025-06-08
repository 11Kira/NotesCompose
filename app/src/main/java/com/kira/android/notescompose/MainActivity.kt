package com.kira.android.notescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kira.android.notescompose.Graph.NOTE_SCREEN_ROUTE
import com.kira.android.notescompose.features.notes.details.NoteScreen
import com.kira.android.notescompose.features.notes.list.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreenView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            if (currentRoute == "note_list") {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("${Graph.NOTE_GRAPH}/${""}")
                    },
                    containerColor = Color("#FFA500".toColorInt()),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Note")
                }
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .consumeWindowInsets(contentPadding)
            .systemBarsPadding()
        ) {
            NavigationGraph(navController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "note_list"
    ) {
        composable("note_list") {
            NoteListScreen(
                onItemClicked = { noteId ->
                    navController.navigate("${Graph.NOTE_GRAPH}/${noteId}")
                }
            )
        }
        noteNavGraph(navController)
    }
}

fun NavGraphBuilder.noteNavGraph(navController: NavHostController) {
    navigation(
        route = "${Graph.NOTE_GRAPH}/{id}",
        startDestination = NOTE_SCREEN_ROUTE
    ) {
        composable(
            route = NOTE_SCREEN_ROUTE,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString("id")
            NoteScreen(id, navController)
        }
    }
}

object Graph {
    const val NOTE_GRAPH = "note_graph"
    const val NOTE_SCREEN_ROUTE = "note/{id}"
}
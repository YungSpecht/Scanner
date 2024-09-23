package com.example.scanner.nav

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.scanner.MainActivity
import com.example.scanner.ui.detailscreen.DocumentScreen
import com.example.scanner.ui.mainscreen.MainScreen
import com.example.scanner.ui.mainscreen.MainScreenViewModel
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner


@Composable
fun AppNavGraph(navController: NavHostController) {

    val viewModel: MainScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAIN // Starting screen is MainScreen
    ) {
        // Main Screen
        composable(NavRoutes.MAIN) {
            MainScreen(
                onDocumentClick = { id ->
                    navController.navigate(NavRoutes.DOCUMENT + "/$id")
                }
            )
        }

        // Document Screen
        composable(
            route = NavRoutes.DOCUMENT + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("id")
            DocumentScreen(id)
        }

    }
}
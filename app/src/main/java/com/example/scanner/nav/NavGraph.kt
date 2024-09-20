package com.example.scanner.nav

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.scanner.MainActivity
import com.example.scanner.ui.DocumentScreen
import com.example.scanner.ui.MainScreen
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import data.SampleData


@Composable
fun AppNavGraph(navController: NavHostController, scanner: GmsDocumentScanner, scannerLauncher: ActivityResultLauncher<IntentSenderRequest>, activity: MainActivity) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAIN // Starting screen is MainScreen
    ) {
        // Main Screen
        composable(NavRoutes.MAIN) {
            MainScreen(
                onDocumentClick = { id ->
                    navController.navigate(NavRoutes.DOCUMENT + "/$id")
                },
                documents = SampleData.getSampleData(),
                scanner = scanner,
                scannerLauncher = scannerLauncher,
                activity = activity
            )
        }

        // Document Screen
        composable(
            route = NavRoutes.DOCUMENT + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id")
            val document = SampleData.getSampleData().find { it.id == id }
            if (document != null) {
                DocumentScreen(document)
            }
        }

    }
}
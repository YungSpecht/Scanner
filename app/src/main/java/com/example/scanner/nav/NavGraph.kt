package com.example.scanner.nav

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.scanner.ui.addscreen.AddDocumentScreen
import com.example.scanner.ui.detailscreen.DetailScreen
import com.example.scanner.ui.mainscreen.MainScreen


@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAIN // Starting screen is MainScreen
    ) {

        // Main Screen
        composable(
            route = NavRoutes.MAIN
        ) {
            MainScreen(
                onDocumentClick = { id ->
                    navController.navigate(NavRoutes.DOCUMENT + "/$id")
                },
                onNewDocumentScanned = { imageUri ->
                    navController.navigate("newDocumentDetail?imageUri=${imageUri.toString()}")
                }
            )
        }

        // Document Screen
        composable(
            route = NavRoutes.DOCUMENT + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("id")?.toLong()
            if (id != null) {
                DetailScreen(
                    id.toLong(),
                    onDelete = {navController.navigate(NavRoutes.MAIN)}
                )
            }
        }

        // Add New Document Screen
        composable(
            route = "newDocumentDetail?imageUri={imageUri}",
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")?.let { Uri.parse(it) }
            if (imageUri != null) {
                AddDocumentScreen(
                    imageUri,
                    onSave = {
                        navController.navigate(NavRoutes.MAIN
                        )
                    }
                )
            }
        }


    }
}
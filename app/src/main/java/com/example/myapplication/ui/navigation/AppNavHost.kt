package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.DetailScreen.DetailScreen
import com.example.myapplication.ui.screens.HomeScreen.HomeScreen
import com.example.myapplication.ui.screens.login.LoginScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login"){
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable(
            "detail/{postId}",
            arguments = listOf(navArgument("postId") {
                type = NavType.IntType
                defaultValue = -1  // Para evitar valores nulos
            })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: -1
            if (postId != -1) {
                DetailScreen(postId)
            } else {
                // Podrías manejar la navegación a una pantalla de error si el postId no es válido
            }
        }

    }
}

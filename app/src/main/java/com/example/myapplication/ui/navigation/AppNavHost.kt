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
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.repository.PrestamoRepositoryLogin

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    repository: PrestamoRepositoryLogin,
    userPreferences: UserPreferences
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController, repository, userPreferences)
        }
        composable(Routes.HOME) {
            HomeScreen(navController,userPreferences)
        }
        composable(
            "detail/{postId}",
            arguments = listOf(navArgument("postId") {
                type = NavType.IntType
                nullable = false
                defaultValue = 0
            })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: 0
            DetailScreen(postId)
        }
    }
}

fun navigateToHome(navController: NavHostController) {
    if (navController.currentBackStackEntry?.destination?.route != Routes.HOME) {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.LOGIN) { inclusive = true }
        }
    }
}

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val DETAIL = "detail/{postId}"
}
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.AppNavHost
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.repository.PrestamoRepositoryLogin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize UserPreferences and PrestamoRepositoryLogin
        val userPreferences = UserPreferences(applicationContext)
        val repository = PrestamoRepositoryLogin(RetrofitClient.apiPrestamoService)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        repository = repository,
                        userPreferences = userPreferences
                    )
                }
            }
        }
    }
}
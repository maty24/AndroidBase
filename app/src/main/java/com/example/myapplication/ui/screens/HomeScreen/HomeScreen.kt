package com.example.myapplication.ui.screens.HomeScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.model.PrestamosPendiente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    userPreferences: UserPreferences,
    homeViewModel: HomeViewModel = viewModel()
) {
    val posts by homeViewModel.posts.collectAsState()
    val prestamos by homeViewModel.prestamos.collectAsState()
    val isLoading by homeViewModel.loading.collectAsState()
    val errorMessage by homeViewModel.error.collectAsState()
    val userName by userPreferences.userName.collectAsState(initial = "User")
    val idUserString by userPreferences.userId.collectAsState(initial = "1")
    val idUser: Int = idUserString?.toIntOrNull() ?: 1

    LaunchedEffect(Unit) {
        if (posts.isEmpty()) { // Solo llamar a fetchPosts si no hay posts
            homeViewModel.fetchPrestamos(idUser)
        }
    }

    LaunchedEffect(idUserString) {
        Log.d("HomeScreen", "idUserString: $idUserString")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Posts de ${userName ?: "User"}") })
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(prestamos, key = { it.id }) { prestamos ->
                        PostItem(prestamos) {
                            navController.navigate("detail/${prestamos.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(prestamos: PrestamosPendiente, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = prestamos.libro.titulo,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = prestamos.libro.categoria.nombre,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
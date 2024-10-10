package com.example.myapplication.ui.screens.DetailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.model.Post


@Composable
fun DetailScreen(postId: Int?, detailViewModel: DetailViewModel = viewModel()) {
    // Obtenemos los estados desde el ViewModel usando StateFlow
    val post by detailViewModel.post.collectAsState()
    val isLoading by detailViewModel.loading.collectAsState()
    val errorMessage by detailViewModel.error.collectAsState()

    // Llamamos a la función fetchPostById solo una vez, cuando se carga la pantalla
    LaunchedEffect(postId) {
        detailViewModel.fetchPostById(postId)
    }

    // Manejamos los diferentes estados de la UI
    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Error desconocido", color = MaterialTheme.colorScheme.error)
            }
        }
        post != null -> {
            // Mostramos los detalles del post
            PostDetail(post!!)
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No se encontró el post.")
            }
        }
    }
}

@Composable
fun PostDetail(post: Post) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Title: ${post.title}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = post.body, style = MaterialTheme.typography.bodyLarge)
    }
}

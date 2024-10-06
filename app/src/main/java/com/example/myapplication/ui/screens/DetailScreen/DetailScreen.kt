package com.example.myapplication.ui.screens.DetailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailScreen(postId: Int?, detailViewModel: DetailViewModel = viewModel()) {
    val post = detailViewModel.post.observeAsState()

    if (postId != null) {
        detailViewModel.fetchPostById(postId)
    }

    post.value?.let {
        Text(text = "Title: ${it.title}\n\nBody: ${it.body}")
    } ?: run {
        Text(text = "Loading...")
    }
}
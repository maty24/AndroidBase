package com.example.myapplication.ui.screens.DetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailViewModel : ViewModel() {

    private val postRepository = PostRepository()

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchPostById(postId: Int) {
        _loading.value = true  // Iniciamos el estado de carga
        viewModelScope.launch {
            try {
                val response = postRepository.getPostById(postId)
                if (response.isSuccessful) {
                    _post.value = response.body()  // Asignamos el post recibido
                    _error.value = null
                } else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                    _post.value = null
                }
            } catch (e: Exception) {
                // Manejamos el error genérico
                _error.value = "Exception: ${e.message}"
                _post.value = null
            } finally {
                _loading.value = false  // Terminamos el estado de carga
            }
        }
    }
}
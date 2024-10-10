package com.example.myapplication.ui.screens.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    private val postRepository = PostRepository()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchPosts() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = postRepository.getPosts()
                if (response.isSuccessful) {
                    _posts.value = response.body() ?: emptyList()
                    _error.value = null // Limpiamos cualquier error anterior si la llamada es exitosa
                } else {
                    handleError("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                handleError("Exception: ${e.message}")
            } finally {
                _loading.value = false // Aseguramos que el estado de carga siempre se actualice
            }
        }
    }

    private fun handleError(message: String) {
        _error.value = message
        _posts.value = emptyList() // Limpiamos la lista de posts si hay error
    }
}

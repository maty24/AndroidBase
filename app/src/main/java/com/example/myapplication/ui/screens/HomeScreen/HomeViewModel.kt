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

    private val _posts =
        MutableStateFlow<List<Post>>(emptyList()) //MutableStateFlow es una clase de Kotlin Coroutines que permite representar un valor que puede cambiar con el tiempo. En este caso, el valor es una lista de Pos
    val posts: StateFlow<List<Post>> = _posts

    private val _loading = MutableStateFlow(false) // Para controlar el estado de carga
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null) // Para mostrar errores
    val error: StateFlow<String?> = _error

    fun fetchPosts() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = postRepository.getPosts()
                if (response.isSuccessful) {
                    _posts.value = response.body() ?: emptyList()
                    _error.value =
                        null  // Limpiamos cualquier error anterior si la llamada es exitosa
                } else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            } finally {
                _loading.value = false  // Aseguramos que el estado de carga siempre se actualice
            }
        }
    }
}
package com.example.myapplication.ui.screens.DetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.PrestamosByID
import com.example.myapplication.data.repository.PostRepository
import com.example.myapplication.data.repository.PrestamoRepositoryLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailViewModel : ViewModel() {

    private val postRepository = PostRepository()
    private val prestamoRepository = PrestamoRepositoryLogin(RetrofitClient.apiPrestamoService)

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _prestamos = MutableStateFlow<PrestamosByID?>(null)
    val prestamos: StateFlow<PrestamosByID?> = _prestamos


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchPostById(postId: Int?) {
        if (_post.value != null) return  // Evitamos hacer la llamada nuevamente si el post ya está cargado

        _loading.value = true
        viewModelScope.launch {
            try {
                val response = postRepository.getPostById(postId)
                if (response.isSuccessful) {
                    _post.value = response.body()  // Asignamos el post recibido
                    _error.value = null  // Limpiamos cualquier error anterior
                } else {
                    handleError("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                handleError("Exception: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    // Cambiamos la visibilidad a 'public' o 'internal' para que pueda ser llamada desde fuera
    fun handleError(message: String) {
        _error.value = message
        _post.value = null
    }
}

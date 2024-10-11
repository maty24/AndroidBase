package com.example.myapplication.ui.screens.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.model.PrestamosPendiente
import com.example.myapplication.data.repository.PrestamoRepositoryLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    private val prestamoRepository = PrestamoRepositoryLogin(RetrofitClient.apiPrestamoService)

    private val _prestamos = MutableStateFlow<List<PrestamosPendiente>>(emptyList())
    val prestamos: StateFlow<List<PrestamosPendiente>> = _prestamos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun fetchPrestamos(id: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = prestamoRepository.getPrestamos(id)
                if (response.isSuccessful) {
                    _prestamos.value = response.body() ?: emptyList()
                    _error.value =
                        null // Limpiamos cualquier error anterior si la llamada es exitosa
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
        _prestamos.value = emptyList() // Limpiamos la lista de posts si hay error
    }
}

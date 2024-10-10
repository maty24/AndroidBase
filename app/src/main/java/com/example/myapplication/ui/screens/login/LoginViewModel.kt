package com.example.myapplication.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.repository.PrestamoRepositoryLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: PrestamoRepositoryLogin,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    fun login(email: String, password: String) {
        _loading.value = true
        _error.value = null
        _success.value = false

        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        // Save the token in DataStore
                        userPreferences.saveUserData(it.id, it.nombre, it.token)
                        _success.value = true // Login successful
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun showError(message: String) {
        _error.value = message
    }
}
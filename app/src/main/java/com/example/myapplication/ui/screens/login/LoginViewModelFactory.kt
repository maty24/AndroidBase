package com.example.myapplication.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.data.repository.PrestamoRepositoryLogin

class LoginViewModelFactory(
    private val repository: PrestamoRepositoryLogin,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
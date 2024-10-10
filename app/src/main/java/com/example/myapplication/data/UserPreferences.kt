package com.example.myapplication.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para crear DataStore en la clase Context
val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    // Función para guardar los datos del usuario
    suspend fun saveUserData(id: Long, name: String, token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id.toString()
            preferences[USER_NAME_KEY] = name
            preferences[TOKEN_KEY] = token
        }
    }

    // Función para obtener el token almacenado
    val token: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    // Otras funciones para obtener el nombre o el id
    val userName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY]
        }

    val userId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY]
        }
}
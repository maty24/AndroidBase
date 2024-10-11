package com.example.myapplication.data.model

data class PrestamosPendiente (
    val id: Long,
    val libroID: Long,
    val libro: Libro,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val fechaDevuelto: String,
    val estado: String
)

data class Libro (
    val id: Long,
    val titulo: String,
    val autorID: Long,
    val categoriaID: Long,
    val categoria: Categoria,
    val fechaPublicacion: String,
    val disponible: Boolean
)

data class PrestamosByID (
    val id: Long,
    val libroID: Long,
    val libro: Libro,
    val lectorID: Long,
    val lector: Lector,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val fechaDevuelto: String,
    val estado: String
)

data class Lector (
    val id: Long,
    val nombre: String,
    val email: String,
    val passwordHash: String,
    val tipoUsuario: String,
    val estado: String,
    val fechaRegistro: String,
    val ultimoInicioSesion: String
)

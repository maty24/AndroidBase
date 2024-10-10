package com.example.myapplication.data.model

data class LibroReponse (
    val id: Long,
    val titulo: String,
    val autorID: Long,
    val categoriaID: Long,
    val categoria: Categoria,
    val fechaPublicacion: String,
    val disponible: Boolean
)

data class Categoria (
    val id: Long,
    val nombre: String
)

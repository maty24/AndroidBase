package com.example.myapplication.data.model

data class Login(
    val email: String,
    val password: String
)


data class ResponseLogin (
    val id: Long,
    val nombre: String,
    val token: String
)

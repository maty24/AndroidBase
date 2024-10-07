package com.example.myapplication.data.api

import com.example.myapplication.data.model.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>  // Ahora es una función suspendida, retorna la lista directamente

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>  // Función suspendida para obtener un post
}

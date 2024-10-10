package com.example.myapplication.data.api

import com.example.myapplication.data.model.Login
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int?): Response<Post>
}


interface PrestamoService {
    @POST("usuario/login")
    suspend fun login(@Body login: Login): Response<ResponseLogin>
}
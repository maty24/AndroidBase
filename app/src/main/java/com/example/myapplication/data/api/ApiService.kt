package com.example.myapplication.data.api

import com.example.myapplication.data.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>
}
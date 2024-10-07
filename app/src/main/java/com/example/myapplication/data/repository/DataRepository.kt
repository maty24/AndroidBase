package com.example.myapplication.data.repository

import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.model.Post
import retrofit2.Response


class PostRepository {

    suspend fun getPosts(): Response<List<Post>> {
        return RetrofitClient.apiService.getPosts()
    }

    suspend fun getPostById(id: Int): Response<Post> {
        return RetrofitClient.apiService.getPostById(id)
    }
}

package com.example.myapplication.data.repository

import com.example.myapplication.data.api.PrestamoService
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.model.Login
import com.example.myapplication.data.model.Post
import com.example.myapplication.data.model.ResponseLogin
import retrofit2.Response


class PostRepository {

    suspend fun getPosts(): Response<List<Post>> {
        return RetrofitClient.apiService.getPosts()
    }

    suspend fun getPostById(id: Int?): Response<Post> {
        return RetrofitClient.apiService.getPostById(id)
    }
}


class PrestamoRepositoryLogin(apiPrestamoService: PrestamoService) {
    suspend fun login(email: String, password: String): Response<ResponseLogin> {
        val loginRequest = Login(email, password)
        return RetrofitClient.apiPrestamoService.login(loginRequest)
    }
}
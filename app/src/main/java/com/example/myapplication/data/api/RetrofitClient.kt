package com.example.myapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL_1 = "https://jsonplaceholder.typicode.com/"
    private const val BASE_URL_2 = "http://10.6.22.9:9001/api/v1/"

    val apiService: PostApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)
    }

    val apiPrestamoService: PrestamoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrestamoService::class.java)
    }
}
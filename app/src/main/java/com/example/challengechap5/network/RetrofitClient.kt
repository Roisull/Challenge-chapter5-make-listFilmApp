package com.example.challengechap5.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val BASE_URL = "https://63417dff16ffb7e275d278b9.mockapi.io/"

    val instance : RetrofitService by lazy {
        val service = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        service.create(RetrofitService::class.java)
    }
}
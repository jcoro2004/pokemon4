package com.example.pokemon4

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Objecte singleton per a la instància de Retrofit
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
package com.example.pokemon4

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("http://10.0.2.2/api.php") // Canvia localhost per 10.0.2.2
    fun getData(): Call<ApiResponse>
}

package com.example.pokemon4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @GET("http://10.0.2.2/api.php")
    fun getData(): Call<ApiResponse>

    @FormUrlEncoded
    @POST("http://10.0.2.2/submit_name.php")
    fun submitName(@Field("name") name: String): Call<Void>

    @FormUrlEncoded
    @POST("http://10.0.2.2/update_score.php")
    fun updateScore(@Field("name") name: String, @Field("score") score: Int): Call<Void>
}
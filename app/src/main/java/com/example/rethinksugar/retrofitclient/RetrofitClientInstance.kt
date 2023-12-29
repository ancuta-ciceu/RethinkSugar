package com.example.rethinksugar.retrofitclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {
    private lateinit var retrofit : Retrofit
    private val BASE_URL = "www.themealdb.com/api/json/v1/1"
    val retrofitInstance:Retrofit
        get() {
            if(retrofit == null){
                retrofit = retrofit2.Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
}
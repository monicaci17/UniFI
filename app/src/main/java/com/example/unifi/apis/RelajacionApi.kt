package com.example.unifi.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RelajacionApi {

    val api: RelaxApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RelaxApiService::class.java)
    }
}
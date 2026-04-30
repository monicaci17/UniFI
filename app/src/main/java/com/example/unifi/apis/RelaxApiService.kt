package com.example.unifi.apis

import com.example.unifi.data.model.Frases
import retrofit2.http.GET

interface RelaxApiService {

    @GET("api/random")
    suspend fun getFraseRandom(): List<Frases>
}
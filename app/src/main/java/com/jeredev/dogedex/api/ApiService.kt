package com.jeredev.dogedex.api

import com.jeredev.dogedex.BASE_URL
import com.jeredev.dogedex.api.response.DogResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build()

interface ApiService{
    @GET("dogs")
    suspend fun getAllDogs() : DogResponse
}

object DogsApi{
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
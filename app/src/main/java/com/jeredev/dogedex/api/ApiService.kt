package com.jeredev.dogedex.api

import com.jeredev.dogedex.BASE_URL
import com.jeredev.dogedex.SING_UP_URL
import com.jeredev.dogedex.api.dto.SingUpDTO
import com.jeredev.dogedex.api.response.DogResponse
import com.jeredev.dogedex.api.response.SingUpApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build()

interface ApiService{
    @GET("dogs")
    suspend fun getAllDogs() : DogResponse

    @POST(SING_UP_URL)
    suspend fun singUp(@Body singUpDTO: SingUpDTO): SingUpApiResponse
}

object DogsApi{
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
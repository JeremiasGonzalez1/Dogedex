package com.jeredev.dogedex.api

import com.jeredev.dogedex.*
import com.jeredev.dogedex.api.dto.AddDogToUserDTO
import com.jeredev.dogedex.api.dto.LoginDTO
import com.jeredev.dogedex.api.dto.SingUpDTO
import com.jeredev.dogedex.api.response.DogListResponse
import com.jeredev.dogedex.api.response.AuthApiResponse
import com.jeredev.dogedex.api.response.DefaultResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val okHttpClient = OkHttpClient.Builder().addInterceptor(ApiServiceInterceptor).build()

private val retrofit = Retrofit
    .Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create()).build()

interface ApiService {
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs(): DogListResponse

    @POST(SING_UP_URL)
    suspend fun singUp(@Body singUpDTO: SingUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body signInDTO: LoginDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
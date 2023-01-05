package com.jeredev.dogedex.api.response

import com.squareup.moshi.Json

data class Dog(
    val id: Int,
    val index: Int,
    val temperament: String,
    val name: String,
    val type: String,
    val heightFemale: String,
    val heightMale: String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val weightFemale: String,
    val weightMale: String
)
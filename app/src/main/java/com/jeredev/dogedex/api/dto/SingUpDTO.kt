package com.jeredev.dogedex.api.dto

import com.squareup.moshi.Json

class SingUpDTO(
    val email: String,
    val password: String,
    @field:Json(name = "password_confirmation") val passwordConfirmation: String
)
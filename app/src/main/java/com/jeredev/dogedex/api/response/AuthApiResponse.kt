package com.jeredev.dogedex.api.response

import com.squareup.moshi.Json

class AuthApiResponse (
    val data: UserResponse,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val message: String
)
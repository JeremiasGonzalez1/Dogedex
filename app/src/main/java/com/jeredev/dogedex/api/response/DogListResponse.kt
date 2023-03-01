package com.jeredev.dogedex.api.response

import com.squareup.moshi.Json

data class DogListResponse(
    val data: Data,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val message: String
)
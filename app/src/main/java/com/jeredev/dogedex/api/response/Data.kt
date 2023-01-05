package com.jeredev.dogedex.api.response

import com.jeredev.dogedex.api.dto.DogDTO

data class Data(
    val dogs: List<DogDTO>
)
package com.jeredev.dogedex.doglist

import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.DogsApi.retrofitService
import com.jeredev.dogedex.api.dto.DogDTOMapper
import com.jeredev.dogedex.api.makeNetWorkCall
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.api.response.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

class DogRepository {
    suspend fun downloadDogs() : ApiResponseStatus<List<Dog>> =
        makeNetWorkCall {
            val dogListResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
}
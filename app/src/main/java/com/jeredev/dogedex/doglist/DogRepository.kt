package com.jeredev.dogedex.doglist

import com.jeredev.dogedex.api.DogsApi.retrofitService
import com.jeredev.dogedex.api.dto.AddDogToUserDTO
import com.jeredev.dogedex.api.dto.DogDTOMapper
import com.jeredev.dogedex.api.makeNetWorkCall
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.Dog

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> =
        makeNetWorkCall {
            val dogListResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }

    suspend fun addDogToUser(dogID: Int): ApiResponseStatus<Any> = makeNetWorkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogID)
        val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

        if (defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> =
        makeNetWorkCall {
            val dogListResponse = retrofitService.getUserDogs()
            val dogDTOList = dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
}
package com.jeredev.dogedex.doglist

import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.DogsApi.retrofitService
import com.jeredev.dogedex.api.dto.AddDogToUserDTO
import com.jeredev.dogedex.api.dto.DogDTOMapper
import com.jeredev.dogedex.api.makeNetWorkCall
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun getAllDogsCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val getAlldogsResponseDeferred = downloadDogs()
            when (getAlldogsResponseDeferred) {
                is ApiResponseStatus.Error -> {
                    ApiResponseStatus.Error(R.string.unknown_error)
                }
                is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(getAlldogsResponseDeferred.data)
                }
                is ApiResponseStatus.Loading->{
                    ApiResponseStatus.Loading()
                }
            }
        }
    }

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListResponseDeferred = async { downloadDogs() }
            val userDogListResponseDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListResponseDeferred.await()
            val userDogListResponse = userDogListResponseDeferred.await()
            when {
                allDogsListResponse is ApiResponseStatus.Error -> {
                    allDogsListResponse
                }
                userDogListResponse is ApiResponseStatus.Error -> {
                    userDogListResponse
                }
                allDogsListResponse is ApiResponseStatus.Success && userDogListResponse is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(
                        getCollectionList(
                            allDogsListResponse.data,
                            userDogListResponse.data
                        )
                    )
                }
                else -> {
                    ApiResponseStatus.Error(R.string.unknown_error)
                }
            }
        }
    }

    private fun getCollectionList(allDogsList: List<Dog>, userDogList: List<Dog>): List<Dog> {
        return allDogsList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(0, it.index, "", "", "", "", "", "", "", "", "", inCollection = false)
            }
        }.sorted()
    }

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

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> =
        makeNetWorkCall {
            val dogListResponse = retrofitService.getUserDogs()
            val dogDTOList = dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
}
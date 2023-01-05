package com.jeredev.dogedex.doglist

import com.jeredev.dogedex.api.DogsApi.retrofitService
import com.jeredev.dogedex.api.dto.DogDTOMapper
import com.jeredev.dogedex.api.response.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    suspend fun downloadDogs() : List<Dog>{
        return withContext(Dispatchers.IO){
            val dogListResponse = retrofitService.getAllDogs()
            val dogDTOList= dogListResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }
}
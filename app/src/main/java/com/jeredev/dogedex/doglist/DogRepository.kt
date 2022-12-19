package com.jeredev.dogedex.doglist

import com.jeredev.dogedex.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    suspend fun downloadDogs() : List<Dog>{
        return withContext(Dispatchers.IO){
            getDogs()
        }
    }

    fun getDogs() = listOf<Dog>()
}
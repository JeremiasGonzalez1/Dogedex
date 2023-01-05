package com.jeredev.dogedex.api.dto

import com.jeredev.dogedex.api.response.Dog

class DogDTOMapper {
    private fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
        return Dog(
            dogDTO.id,
            dogDTO.index,
            dogDTO.name,
            dogDTO.type,
            dogDTO.heightFemale,
            dogDTO.heightMale,
            dogDTO.imageUrl,
            dogDTO.lifeExpectancy,
            dogDTO.temperament,
            dogDTO.weightFemale,
            dogDTO.weightMale
        )
    }

    fun fromDogDTOListToDogDomainList(dogDTOList: List<DogDTO>) : List<Dog>{
        return dogDTOList.map{fromDogDTOToDogDomain(it)}
    }
}
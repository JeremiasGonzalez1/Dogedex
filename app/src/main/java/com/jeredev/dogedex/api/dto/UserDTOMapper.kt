package com.jeredev.dogedex.api.dto

import com.jeredev.dogedex.model.User

class UserDTOMapper {
    fun fromUserDTOToUser(userDTO: UserDTO) = User(
        userDTO.id,
        userDTO.email,
        userDTO.authenticationToken
    )

}
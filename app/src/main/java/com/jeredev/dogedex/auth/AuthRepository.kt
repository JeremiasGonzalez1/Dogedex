package com.jeredev.dogedex.auth

import com.jeredev.dogedex.api.DogsApi
import com.jeredev.dogedex.api.dto.SingUpDTO
import com.jeredev.dogedex.api.dto.UserDTOMapper
import com.jeredev.dogedex.api.makeNetWorkCall
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.User

class AuthRepository {

    suspend fun singUp(
        email: String,
        password: String,
        passwordRepeat: String
    ): ApiResponseStatus<User> =
        makeNetWorkCall {
            val singUpDTO = SingUpDTO(email, password, passwordRepeat)
            val singUpResponse = DogsApi.retrofitService.singUp(singUpDTO)
            if (!singUpResponse.isSuccess) {
                throw Exception(singUpResponse.message)
            }

            val userDTO = singUpResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUser(userDTO)
        }
}
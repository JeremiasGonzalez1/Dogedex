package com.jeredev.dogedex.auth

import com.jeredev.dogedex.api.DogsApi
import com.jeredev.dogedex.api.dto.LoginDTO
import com.jeredev.dogedex.api.dto.SingUpDTO
import com.jeredev.dogedex.api.dto.UserDTOMapper
import com.jeredev.dogedex.api.makeNetWorkCall
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.User

class AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): ApiResponseStatus<User> =
        makeNetWorkCall {
            val loginDTO = LoginDTO(email, password)
            val loginResponse = DogsApi.retrofitService.login(loginDTO)
            if (!loginResponse.isSuccess) {
                throw Exception(loginResponse.message)
            }

            val userDTO = loginResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUser(userDTO)
        }

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
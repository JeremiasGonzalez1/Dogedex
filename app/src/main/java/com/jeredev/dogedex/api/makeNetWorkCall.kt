package com.jeredev.dogedex.api

import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.dto.DogDTOMapper
import com.jeredev.dogedex.api.response.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

suspend fun <T> makeNetWorkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponseStatus.Success(call())
    } catch (e: UnknownHostException) {
        ApiResponseStatus.Error(R.string.unknown_host_exception)
    } catch (e: Exception) {
      val errorMessage =  when(e.message){
            "sign_up_error" -> R.string.error_sign_up
            "sign_in_error" -> R.string.error_sign_in
            "user_already_exists" -> R.string.error_user_already_exists
            else->R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    }
}
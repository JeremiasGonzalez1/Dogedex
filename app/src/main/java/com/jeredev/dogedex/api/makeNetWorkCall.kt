package com.jeredev.dogedex.api

import com.jeredev.dogedex.R
import com.jeredev.dogedex.api.dto.DogDTOMapper
import com.jeredev.dogedex.api.response.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

private const val UNAUTHORIZE_ERROR_CODE = 401
suspend fun <T> makeNetWorkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponseStatus.Success(call())
    } catch (e: UnknownHostException) {
        ApiResponseStatus.Error(R.string.unknown_host_exception)
    } catch (e: HttpException) {
        val errorMessage =
            if (e.code() == UNAUTHORIZE_ERROR_CODE) R.string.wrong_user_or_password else R.string.unknown_error
        ApiResponseStatus.Error(errorMessage)
    } catch (e: Exception) {
        val errorMessage = when (e.message) {
            "sign_up_error" -> R.string.error_sign_up
            "sign_in_error" -> R.string.error_sign_in
            "user_already_exists" -> R.string.error_user_already_exists
            "error_adding_dog" -> R.string.error_adding_dog
            else -> R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    }
}
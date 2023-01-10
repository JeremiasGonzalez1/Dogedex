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
        ApiResponseStatus.Error(R.string.unknown_error)
    }
}
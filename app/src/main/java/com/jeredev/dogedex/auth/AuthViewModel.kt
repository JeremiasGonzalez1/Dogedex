package com.jeredev.dogedex.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.Dog
import com.jeredev.dogedex.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    var user = mutableStateOf<User?>(null)
        private set


    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    fun singUp(email: String, password: String, passwordRepeat: String) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.singUp(email, password, passwordRepeat))
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.login(email, password))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            user.value = apiResponseStatus.data!!
        }
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}
package com.jeredev.dogedex.auth

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

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>>
        get() = _status

    fun singUp(email: String, password: String, passwordRepeat: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.singUp(email, password, passwordRepeat))
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.login(email, password))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _user.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus
    }
}
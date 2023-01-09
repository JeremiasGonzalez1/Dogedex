package com.jeredev.dogedex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.api.response.Dog
import kotlinx.coroutines.launch
import java.lang.Exception


class DogListViewModel : ViewModel() {
    private val repository = DogRepository()

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status : LiveData<ApiResponseStatus>
        get() = _status

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.LOADING
            try {
                _dogList.value = repository.downloadDogs()
                _status.value = ApiResponseStatus.SUCCESS
            }catch (e: Exception){
                _status.value = ApiResponseStatus.ERROR
            }
        }
    }
}
package com.jeredev.dogedex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.Dog
import kotlinx.coroutines.launch


class DogListViewModel : ViewModel() {
    private val repository = DogRepository()

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status

    init {
        getDogCollection()
    }

    fun addDogToUser(dogId: Int) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(repository.addDogToUser(dogId))
        }
    }

        private fun getDogCollection(){
            viewModelScope.launch {
                _status.value = ApiResponseStatus.Loading()
                handleResponseStatus(repository.getDogCollection())
            }
        }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dogList.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            getDogCollection()
        }
        _status.value = apiResponseStatus
    }

}
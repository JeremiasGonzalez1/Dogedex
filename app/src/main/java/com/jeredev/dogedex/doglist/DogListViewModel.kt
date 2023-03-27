package com.jeredev.dogedex.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeredev.dogedex.api.response.ApiResponseStatus
import com.jeredev.dogedex.model.Dog
import kotlinx.coroutines.launch


class DogListViewModel : ViewModel() {
    private val repository = DogRepository()

    var dogList = mutableStateOf<List<Dog>>(listOf())
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    var state = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    private val _allListDogs = MutableLiveData<List<Dog>>()
    val allListDogs: LiveData<List<Dog>>
        get() = _allListDogs

    init {
        getDogCollection()
        getAllDogList()
    }

    fun addDogToUser(dogId: Int) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(repository.addDogToUser(dogId))
        }
    }

    private fun getDogCollection() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(repository.getDogCollection())
        }
    }

    private fun getAllDogList() {
        viewModelScope.launch {
            handleResponseStatusListDog(repository.getAllDogsCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            dogList.value = apiResponseStatus.data!!
        }
        status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatusListDog(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _allListDogs.value = apiResponseStatus.data!!
        }
        status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            getDogCollection()
        }
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

}
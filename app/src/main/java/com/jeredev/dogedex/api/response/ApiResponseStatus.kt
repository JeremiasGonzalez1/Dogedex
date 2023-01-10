package com.jeredev.dogedex.api.response

sealed class ApiResponseStatus() {
    class Success(val dogList: List<Dog>):ApiResponseStatus()
    class Loading():ApiResponseStatus()
    class Error(val message : Int):ApiResponseStatus()
}

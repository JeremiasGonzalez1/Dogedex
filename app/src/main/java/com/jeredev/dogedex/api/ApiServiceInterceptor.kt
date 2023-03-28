package com.jeredev.dogedex.api

import okhttp3.Interceptor
import okhttp3.Response

object ApiServiceInterceptor : Interceptor {

    const val NEEDS_AUTH_HEADER_KEY = "needs_auth_header_key"

    private var sessionToken: String? = null

    fun setSessionToken(sessionToken: String) {
        this.sessionToken = sessionToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        if (request.headers[NEEDS_AUTH_HEADER_KEY] != null) {
            //needs credentials
            if (sessionToken == null) {
                throw java.lang.RuntimeException("Need to be authenticated to perform")
            } else {
                requestBuilder.addHeader("AUTH-TOKEN", sessionToken!!)
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}
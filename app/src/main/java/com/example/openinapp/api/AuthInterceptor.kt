package com.example.openinapp.api

import com.example.openinapp.utils.Constants.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptors{

    companion object : Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()

            request.addHeader("Authorization", "Bearer $TOKEN")
            return chain.proceed(request.build())
        }
    }
}
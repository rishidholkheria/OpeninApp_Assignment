package com.example.openinapp.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response



class AuthInterceptors(context: Context){
    init {
        Companion.context = context
    }

    companion object : Interceptor{
        private lateinit var context: Context


        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            if (!Companion::context.isInitialized) {
                throw IllegalStateException("Context is not initialized. Call AuthInterceptors(context: Context) first.")
            }

            val tokenManager = TokenManager(context)
            val token = tokenManager.getToken()
            request.addHeader("Authorization", "Bearer $token")
            return chain.proceed(request.build())
        }
    }
}
package com.example.openinapp.network

import com.example.openinapp.api.AuthInterceptors
import com.example.openinapp.api.LinksAPI
import com.example.openinapp.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
//
//    fun providesRetrofit(): Retrofit.Builder {
//        return Retrofit.Builder().baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//    }
//
//    fun okhttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(AuthInterceptors())
//            .build()
//    }
//
//    fun providesLinkAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): LinksAPI {
//        return retrofitBuilder
//            .client(okHttpClient)
//            .build().create(LinksAPI::class.java)
//    }

}
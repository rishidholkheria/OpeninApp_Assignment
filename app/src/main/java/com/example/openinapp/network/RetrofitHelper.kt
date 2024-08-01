package com.example.openinapp.network

import android.content.Context
import com.example.openinapp.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

//    Creating Retrofit Instance with Okhttp to pass authentication header
    fun getInstance(context: Context): Retrofit {
        AuthInterceptors(context)
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptors).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}
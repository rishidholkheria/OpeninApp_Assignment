package com.example.openinapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openinapp.api.LinksAPI
import com.example.openinapp.model.LinksResponse
import com.example.openinapp.network.NetworkResult
import retrofit2.Response

class LinksRepository(private val linksAPI: LinksAPI) {

    private val _linksLiveData = MutableLiveData<NetworkResult<LinksResponse>>()
    val linksLiveData: LiveData<NetworkResult<LinksResponse>>
        get() =_linksLiveData

    suspend fun getLinks(){
        val response = linksAPI.getLinksData()
        handleResponse(response)
    }

    private fun handleResponse(response: Response<LinksResponse>) {
        if(response.isSuccessful && response.body()?.data != null){
            _linksLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else {
            _linksLiveData.postValue(NetworkResult.Error(response.body()!!.message))
        }
    }

}
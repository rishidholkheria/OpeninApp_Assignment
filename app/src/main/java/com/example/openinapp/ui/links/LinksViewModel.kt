package com.example.openinapp.ui.links

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinapp.model.LinksResponse
import com.example.openinapp.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LinksViewModel(private val linksRepository: LinksRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            linksRepository.getLinks()
        }
    }

    val linkLiveData: LiveData<NetworkResult<LinksResponse>>
        get() = linksRepository.linksLiveData
}
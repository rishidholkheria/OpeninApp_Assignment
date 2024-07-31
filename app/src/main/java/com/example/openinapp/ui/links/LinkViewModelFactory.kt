package com.example.openinapp.ui.links

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openinapp.repository.LinksRepository

class LinkViewModelFactory(private val repository: LinksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LinksViewModel(repository) as T
    }
}
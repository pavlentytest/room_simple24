package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.DictRepository

class DictViewModelFactory(private val repository: DictRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(DictViewModel::class.java)) DictViewModel(repository) as T
        else throw IllegalArgumentException("Unknown View Model class")
}
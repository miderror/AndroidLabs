package com.example.androiddev

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ActivityViewModelFactory(private val repository: ActivityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}
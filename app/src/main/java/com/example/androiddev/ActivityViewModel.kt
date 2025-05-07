package com.example.androiddev

import androidx.lifecycle.ViewModel

class ActivityViewModel(private val repository: ActivityRepository) : ViewModel() {
    val allActivities = repository.allActivities
}

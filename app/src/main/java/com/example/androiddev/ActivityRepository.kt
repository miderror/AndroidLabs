package com.example.androiddev

import androidx.lifecycle.LiveData

class ActivityRepository(private val activityDao: ActivityDao) {
    val allActivities: LiveData<List<ActivityEntity>> = activityDao.getAll()

    suspend fun insert(activity: ActivityEntity) {
        activityDao.insert(activity)
    }
}
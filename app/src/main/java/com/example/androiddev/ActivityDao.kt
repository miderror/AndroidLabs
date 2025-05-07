package com.example.androiddev

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivityDao {
    @Insert
    suspend fun insert(activity: ActivityEntity)

    @Query("SELECT * FROM activities ORDER BY startTime DESC")
    fun getAll(): LiveData<List<ActivityEntity>>
}
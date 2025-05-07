package com.example.androiddev

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: ActivityType,
    val startTime: Date,
    val endTime: Date,
    val coordinates: List<Coordinate>
) {
    data class Coordinate(val latitude: Double, val longitude: Double)
}
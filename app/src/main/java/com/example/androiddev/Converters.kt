package com.example.androiddev

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let { Date(it) }
    }

    @TypeConverter
    fun fromCoordinates(coordinates: List<ActivityEntity.Coordinate>): String {
        return Gson().toJson(coordinates)
    }

    @TypeConverter
    fun toCoordinates(coordinatesString: String): List<ActivityEntity.Coordinate> {
        val type = object : TypeToken<List<ActivityEntity.Coordinate>>() {}.type
        return Gson().fromJson(coordinatesString, type)
    }

    @TypeConverter
    fun fromActivityType(type: ActivityType): String {
        return type.name
    }

    @TypeConverter
    fun toActivityType(typeString: String): ActivityType {
        return ActivityType.valueOf(typeString)
    }
}
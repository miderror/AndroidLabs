package com.example.androiddev

enum class ActivityType {
    CYCLING,
    RUNNING,
    WALKING
}

fun ActivityType.readableName(): String {
    return when (this) {
        ActivityType.CYCLING -> "Велосипед"
        ActivityType.RUNNING -> "Бег"
        ActivityType.WALKING -> "Шаг"
    }
}
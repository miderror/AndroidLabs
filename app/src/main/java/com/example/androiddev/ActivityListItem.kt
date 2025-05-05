package com.example.androiddev

sealed class ActivityListItem {
    data class DateHeader(val date: String) : ActivityListItem()
    data class ActivityItem(
        val distance: String,
        val duration: String,
        val type: String,
        val timeAgo: String,
        val startTime: String,
        val endTime: String,
        val username: String? = null
    ) : ActivityListItem()
}

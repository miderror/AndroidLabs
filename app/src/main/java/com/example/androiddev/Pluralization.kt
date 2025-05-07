package com.example.androiddev

import kotlin.math.absoluteValue

object Pluralization {
    enum class Case { NOMINATIVE, GENITIVE }

    private fun pluralize(count: Int, case: Case, one: String, two: String, five: String): String {
        val n = count.absoluteValue % 100
        return when {
            n in 11..14 -> "$count $five"
            n % 10 == 1 -> "$count $one"
            n % 10 in 2..4 -> "$count $two"
            else -> "$count $five"
        }
    }

    fun day(count: Int) = pluralize(count, Case.NOMINATIVE, "день", "дня", "дней")
    fun hour(count: Int) = pluralize(count, Case.NOMINATIVE, "час", "часа", "часов")
    fun minute(count: Int) = pluralize(count, Case.NOMINATIVE, "минута", "минуты", "минут")

    fun dayGenitive(count: Int) = pluralize(count, Case.GENITIVE, "дня", "дней", "дней")
    fun hourGenitive(count: Int) = pluralize(count, Case.GENITIVE, "часа", "часов", "часов")
    fun minuteGenitive(count: Int) = pluralize(count, Case.GENITIVE, "минуты", "минут", "минут")
}
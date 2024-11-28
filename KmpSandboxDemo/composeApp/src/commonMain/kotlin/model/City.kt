package model

import kotlinx.datetime.TimeZone

data class City(
    val name: String,
    val timeZone: TimeZone
)
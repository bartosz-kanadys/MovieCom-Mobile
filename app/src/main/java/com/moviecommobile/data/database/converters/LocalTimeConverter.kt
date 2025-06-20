package com.moviecommobile.data.database.converters

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}
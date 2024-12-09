package com.haiderdev94.noteapp.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromTimestampToString(value: Long?): String? {
        return value?.let {
            fromTimestamp(value)?.let { it1 ->
                SimpleDateFormat(
                    "yyyy-MM-dd hh:mm",
                    Locale.getDefault()
                ).format(it1)
            }
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
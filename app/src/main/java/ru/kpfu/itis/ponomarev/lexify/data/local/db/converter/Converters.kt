package ru.kpfu.itis.ponomarev.lexify.data.local.db.converter

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun dateFromTimestamp(ts: Long): Date {
        return Date(ts)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}
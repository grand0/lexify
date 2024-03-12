package ru.kpfu.itis.ponomarev.lexify.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.kpfu.itis.ponomarev.lexify.data.local.db.converter.Converters
import java.util.Date

@Entity(tableName = "loved")
@TypeConverters(Converters::class)
data class LovedWordEntity(
    @PrimaryKey val word: String,
    val date: Date,
)

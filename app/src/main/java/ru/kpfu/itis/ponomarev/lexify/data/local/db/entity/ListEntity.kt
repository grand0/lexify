package ru.kpfu.itis.ponomarev.lexify.data.local.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.kpfu.itis.ponomarev.lexify.data.local.db.converter.Converters
import java.util.Date

@Entity(tableName = "lists")
@TypeConverters(Converters::class)
data class ListEntity(
    @PrimaryKey val name: String,
    val date: Date,
)

@Entity(
    tableName = "list_definitions",
    primaryKeys = ["id", "listName"],
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["name"],
            childColumns = ["listName"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ListDefinitionEntity(
    val id: String,
    val listName: String,
    val word: String,
    val text: String,
    val partOfSpeech: String?,
    val labelsStr: String?,
)
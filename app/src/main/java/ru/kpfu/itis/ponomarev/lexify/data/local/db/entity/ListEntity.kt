package ru.kpfu.itis.ponomarev.lexify.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import ru.kpfu.itis.ponomarev.lexify.data.local.db.converter.Converters
import java.util.Date

@Entity(tableName = "lists")
@TypeConverters(Converters::class)
data class ListEntity(
    @PrimaryKey val name: String,
    val date: Date,
)

@Entity(tableName = "list_definitions")
data class ListDefinitionEntity(
    @PrimaryKey val id: String,
    val word: String,
    val text: String,
    @ColumnInfo(name = "part_of_speech") val partOfSpeech: String?,
)

@Entity(
    tableName = "list_definition_cross_ref",
    primaryKeys = ["name", "id"]
)
data class ListDefinitionCrossRef(
    @ColumnInfo(name = "name") val listName: String,
    @ColumnInfo(name = "id") val definitionId: String,
)

@Entity(
    tableName = "list_definitions_labels",
    foreignKeys = [
        ForeignKey(
            entity = ListDefinitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["definition_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ListDefinitionLabelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "definition_id") val definitionId: String,
    val type: String,
    val text: String,
)

data class ListDefinitionWithLabelsEntity(
    @Embedded val listDefinition: ListDefinitionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "definition_id",
    )
    val labels: List<ListDefinitionLabelEntity>,
)

data class ListWithListDefinitionsAndLabelsEntity(
    @Embedded val list: ListEntity,
    @Relation(
        entity = ListDefinitionEntity::class,
        parentColumn = "name",
        entityColumn = "id",
        associateBy = Junction(
            value = ListDefinitionCrossRef::class,
            parentColumn = "name",
            entityColumn = "id",
        )
    )
    val definitions: List<ListDefinitionWithLabelsEntity>,
)

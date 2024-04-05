package ru.kpfu.itis.ponomarev.lexify.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.ponomarev.lexify.data.local.db.dao.ListsDao
import ru.kpfu.itis.ponomarev.lexify.data.local.db.dao.LovedWordDao
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionCrossRef
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionLabelEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.LovedWordEntity

@Database(
    entities = [
        LovedWordEntity::class,
        ListEntity::class,
        ListDefinitionEntity::class,
        ListDefinitionLabelEntity::class,
        ListDefinitionCrossRef::class,
    ],
    version = 4,
)
abstract class LexifyDatabase : RoomDatabase() {
    abstract val lovedDao: LovedWordDao
    abstract val listsDao: ListsDao
}
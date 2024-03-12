package ru.kpfu.itis.ponomarev.lexify.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.ponomarev.lexify.data.local.db.dao.LovedWordDao
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.LovedWordEntity

@Database(
    entities = [LovedWordEntity::class],
    version = 1,
)
abstract class LexifyDatabase : RoomDatabase() {
    abstract val lovedDao: LovedWordDao
}
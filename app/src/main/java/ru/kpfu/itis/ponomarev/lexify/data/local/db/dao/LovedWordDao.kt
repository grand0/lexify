package ru.kpfu.itis.ponomarev.lexify.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.LovedWordEntity

@Dao
interface LovedWordDao {

    @Query("SELECT * FROM loved")
    fun getAll(): List<LovedWordEntity>

    @Query("SELECT * FROM loved ORDER BY date DESC LIMIT :limit")
    fun getRecent(limit: Int): List<LovedWordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: LovedWordEntity)

    @Query("DELETE FROM loved WHERE word = :word")
    fun deleteWord(word: String)

    @Query("SELECT EXISTS(SELECT * FROM loved WHERE word = :word)")
    fun isWordExists(word: String): Boolean
}
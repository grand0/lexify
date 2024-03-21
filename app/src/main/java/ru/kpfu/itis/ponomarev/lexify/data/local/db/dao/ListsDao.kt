package ru.kpfu.itis.ponomarev.lexify.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListEntity
import java.util.Date

@Dao
interface ListsDao {

    @Query("SELECT * FROM lists")
    fun getLists(): List<ListEntity>

    @Query("SELECT * FROM lists ORDER BY date DESC LIMIT :limit")
    fun getRecent(limit: Int): List<ListEntity>

    @Query("SELECT * FROM list_definitions WHERE listName = :name")
    fun getDefinitionsOfList(name: String): List<ListDefinitionEntity>

    @Insert
    fun createList(list: ListEntity)

    @Query("DELETE FROM lists WHERE name = :name")
    fun deleteList(name: String)

    @Insert
    fun addDefinition(definition: ListDefinitionEntity)

    @Query("DELETE FROM list_definitions WHERE id = :id AND listName = :listName")
    fun deleteDefinition(id: String, listName: String)

    @Query("SELECT :id IN (SELECT id FROM list_definitions WHERE listName = :listName)")
    fun checkDefinition(id: String, listName: String): Boolean
}
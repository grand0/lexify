package ru.kpfu.itis.ponomarev.lexify.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionLabelEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListWithListDefinitionsAndLabelsEntity

@Dao
interface ListsDao {

    @Query("SELECT * FROM lists")
    fun getLists(): List<ListEntity>

    @Query("SELECT * FROM lists ORDER BY date DESC LIMIT :limit")
    fun getRecent(limit: Int): List<ListEntity>

    @Query("SELECT * FROM lists WHERE name = :name")
    fun getDefinitionsOfList(name: String): ListWithListDefinitionsAndLabelsEntity

    @Insert
    fun createList(list: ListEntity)

    @Query("DELETE FROM lists WHERE name = :name")
    fun deleteList(name: String)

    @Insert
    fun addDefinition(definition: ListDefinitionEntity)

    @Query("SELECT :id IN (SELECT id FROM list_definitions WHERE id = :id)")
    fun checkDefinition(id: String): Boolean

    @Query("INSERT INTO list_definition_cross_ref (id, name) VALUES (:id, :listName)")
    fun addDefinitionToList(id: String, listName: String)

    @Insert
    fun addDefinitionLabels(labels: List<ListDefinitionLabelEntity>)

    @Query("DELETE FROM list_definition_cross_ref WHERE id = :id AND name = :listName")
    fun deleteDefinition(id: String, listName: String)

    @Query("SELECT :id IN (SELECT id FROM list_definition_cross_ref WHERE name = :listName)")
    fun checkDefinition(id: String, listName: String): Boolean
}
package ru.kpfu.itis.ponomarev.lexify.domain.repository

import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.ListsSorting

interface ListsRepository {
    suspend fun getAll(sorting: ListsSorting): List<ListModel>
    suspend fun getRecent(limit: Int): List<ListModel>
    suspend fun getDefinitionsOfList(listName: String): List<ListDefinitionModel>
    suspend fun createList(name: String)
    suspend fun deleteList(name: String)
    suspend fun addDefinition(def: WordDefinitionModel, listName: String, word: String)
    suspend fun deleteDefinition(id: String, listName: String)
    suspend fun checkDefinition(id: String, listName: String): Boolean
}
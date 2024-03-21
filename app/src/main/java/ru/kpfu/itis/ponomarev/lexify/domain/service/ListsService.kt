package ru.kpfu.itis.ponomarev.lexify.domain.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.ponomarev.lexify.data.local.db.LexifyDatabase
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListEntity
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionsModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionLabelModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.presentation.sorting.ListsSorting
import java.util.Calendar
import javax.inject.Inject

class ListsService @Inject constructor(
    private val db: LexifyDatabase,
) {

    suspend fun getAll(sorting: ListsSorting = ListsSorting.RECENT): List<ListModel> {
        return withContext(Dispatchers.IO) {
            val list = db.listsDao.getLists()
            when (sorting) {
                ListsSorting.ALPHABETICALLY -> list.sortedBy { it.name.lowercase() }
                ListsSorting.RECENT -> list.sortedBy(ListEntity::date).reversed()
            }.map(::listEntityToModel)
        }
    }

    suspend fun getRecent(limit: Int = 5): List<ListModel> {
        return withContext(Dispatchers.IO) {
            db.listsDao.getRecent(limit).map(::listEntityToModel)
        }
    }

    suspend fun getDefinitionsOfList(name: String): ListDefinitionsModel {
        return withContext(Dispatchers.IO) {
            defsEntitiesToModel(db.listsDao.getDefinitionsOfList(name))
        }
    }

    suspend fun createList(name: String) {
        withContext(Dispatchers.IO) {
            db.listsDao.createList(
                ListEntity(
                    name = name,
                    date = Calendar.getInstance().time,
                )
            )
        }
    }

    suspend fun deleteList(name: String) {
        withContext(Dispatchers.IO) {
            db.listsDao.deleteList(name)
        }
    }

    suspend fun addDefinition(def: WordDefinitionModel, listName: String, word: String) {
        withContext(Dispatchers.IO) {
            db.listsDao.addDefinition(defModelToEntity(def, listName, word))
        }
    }

    suspend fun deleteDefinition(id: String, listName: String) {
        withContext(Dispatchers.IO) {
            db.listsDao.deleteDefinition(id, listName)
        }
    }

    suspend fun checkDefinition(id: String, listName: String): Boolean {
        return withContext(Dispatchers.IO) {
            db.listsDao.checkDefinition(id, listName)
        }
    }

    private fun listEntityToModel(entity: ListEntity): ListModel {
        return ListModel(name = entity.name)
    }

    private fun defsEntitiesToModel(list: List<ListDefinitionEntity>): ListDefinitionsModel {
        return ListDefinitionsModel(
            list
                .groupBy { it.word }
                .mapValues { (word, defs) ->
                    defs.map { def ->
                        WordDefinitionModel(
                            id = def.id,
                            partOfSpeech = def.partOfSpeech,
                            attributionText = "",
                            attributionUrl = "",
                            text = def.text,
                            labels = def.labelsStr
                                ?.split(", ")
                                ?.map {
                                    WordDefinitionLabelModel("", it)
                                } ?: emptyList()
                        )
                    }
                }
        )
    }

    private fun defModelToEntity(def: WordDefinitionModel, listName: String, word: String): ListDefinitionEntity {
        return ListDefinitionEntity(
            id = def.id,
            listName = listName,
            word = word,
            text = def.text,
            partOfSpeech = def.partOfSpeech,
            labelsStr = def.labels.joinToString { it.text },
        )
    }
}
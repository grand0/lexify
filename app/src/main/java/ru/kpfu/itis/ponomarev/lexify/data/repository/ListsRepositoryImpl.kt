package ru.kpfu.itis.ponomarev.lexify.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.ponomarev.lexify.data.local.db.LexifyDatabase
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListDefinitionLabelEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.ListEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.mapper.ListDefinitionEntityMapper
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.mapper.ListEntityMapper
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.ListModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.ListsRepository
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.ListsSorting
import java.util.Calendar
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor(
    private val db: LexifyDatabase,
    private val listEntityMapper: ListEntityMapper,
    private val listDefinitionEntityMapper: ListDefinitionEntityMapper,
) : ListsRepository {
    override suspend fun getAll(sorting: ListsSorting): List<ListModel> {
        return withContext(Dispatchers.IO) {
            val lists = db.listsDao.getLists()
            when (sorting) {
                ListsSorting.ALPHABETICALLY -> lists.sortedBy { it.name.lowercase() }
                ListsSorting.RECENT -> lists.sortedBy(ListEntity::date).reversed()
            }.map(listEntityMapper::mapEntityToModel)
        }
    }

    override suspend fun getRecent(limit: Int): List<ListModel> {
        return withContext(Dispatchers.IO) {
            db.listsDao.getRecent(limit).map(listEntityMapper::mapEntityToModel)
        }
    }

    override suspend fun getDefinitionsOfList(listName: String): List<ListDefinitionModel> {
        return withContext(Dispatchers.IO) {
            db.listsDao.getDefinitionsOfList(listName).definitions.map {
                listDefinitionEntityMapper.mapEntityToModel(it, listName)
            }
        }
    }

    override suspend fun createList(name: String) {
        withContext(Dispatchers.IO) {
            db.listsDao.createList(
                ListEntity(
                    name = name,
                    date = Calendar.getInstance().time,
                )
            )
        }
    }

    override suspend fun deleteList(name: String) {
        withContext(Dispatchers.IO) {
            db.listsDao.deleteList(name)
        }
    }

    override suspend fun addDefinition(def: WordDefinitionModel, listName: String, word: String) {
        withContext(Dispatchers.IO) {
            if (!db.listsDao.checkDefinition(def.id)) {
                db.listsDao.addDefinition(ListDefinitionEntity(
                    id = def.id,
                    word = word,
                    text = def.text,
                    partOfSpeech = def.partOfSpeech,
                ))
                db.listsDao.addDefinitionLabels(
                    def.labels.map {
                        ListDefinitionLabelEntity(
                            id = 0,
                            definitionId = def.id,
                            type = it.type,
                            text = it.text,
                        )
                    }
                )
            }
            db.listsDao.addDefinitionToList(def.id, listName)
        }
    }

    override suspend fun deleteDefinition(id: String, listName: String) {
        withContext(Dispatchers.IO) {
            db.listsDao.deleteDefinition(id, listName)
        }
    }

    override suspend fun checkDefinition(id: String, listName: String): Boolean {
        return withContext(Dispatchers.IO) {
            db.listsDao.checkDefinition(id, listName)
        }
    }
}
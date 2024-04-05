package ru.kpfu.itis.ponomarev.lexify.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.ponomarev.lexify.data.local.db.LexifyDatabase
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.LovedWordEntity
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.mapper.LovedWordEntityMapper
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.LovedRepository
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.LovedWordsSorting
import java.util.Calendar
import javax.inject.Inject

class LovedRepositoryImpl @Inject constructor(
    private val db: LexifyDatabase,
    private val lovedWordEntityMapper: LovedWordEntityMapper,
) : LovedRepository {
    override suspend fun getAll(sorting: LovedWordsSorting): List<LovedWordModel> {
        return withContext(Dispatchers.IO) {
            val list = db.lovedDao.getAll()
            when (sorting) {
                LovedWordsSorting.ALPHABETICALLY -> list.sortedBy { it.word.lowercase() }
                LovedWordsSorting.RECENT -> list.sortedBy(LovedWordEntity::date).reversed()
            }.map(lovedWordEntityMapper::mapEntityToModel)
        }
    }

    override suspend fun getRecent(limit: Int): List<LovedWordModel> {
        return withContext(Dispatchers.IO) {
            db.lovedDao.getRecent(limit).map(lovedWordEntityMapper::mapEntityToModel)
        }
    }

    override suspend fun addWord(word: String) {
        withContext(Dispatchers.IO) {
            db.lovedDao.addWord(
                LovedWordEntity(
                    word = word,
                    date = Calendar.getInstance().time,
                )
            )
        }
    }

    override suspend fun deleteWord(word: String) {
        withContext(Dispatchers.IO) {
            db.lovedDao.deleteWord(word)
        }
    }

    override suspend fun isLoved(word: String): Boolean {
        return withContext(Dispatchers.IO) {
            db.lovedDao.isWordExists(word)
        }
    }
}
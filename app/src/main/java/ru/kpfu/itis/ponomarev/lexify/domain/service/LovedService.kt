package ru.kpfu.itis.ponomarev.lexify.domain.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.ponomarev.lexify.data.local.db.LexifyDatabase
import ru.kpfu.itis.ponomarev.lexify.data.local.db.entity.LovedWordEntity
import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.presentation.sorting.LovedWordsSorting
import java.util.Calendar
import javax.inject.Inject

class LovedService @Inject constructor(
    private val db: LexifyDatabase,
) {

    suspend fun getAll(sorting: LovedWordsSorting = LovedWordsSorting.ALPHABETICALLY): List<LovedWordModel> {
        return withContext(Dispatchers.IO) {
            val list = db.lovedDao.getAll()
            when (sorting) {
                LovedWordsSorting.ALPHABETICALLY -> list.sortedBy { it.word.lowercase() }
                LovedWordsSorting.RECENT -> list.sortedBy(LovedWordEntity::date).reversed()
            }.map(::toModel)
        }
    }

    suspend fun getRecent(count: Int): List<LovedWordModel> {
        return withContext(Dispatchers.IO) {
            db.lovedDao.getRecent(count).map(::toModel)
        }
    }

    suspend fun addWord(word: String) {
        withContext(Dispatchers.IO) {
            db.lovedDao.addWord(
                LovedWordEntity(
                    word = word,
                    date = Calendar.getInstance().time,
                )
            )
        }
    }

    suspend fun deleteWord(word: String) {
        withContext(Dispatchers.IO) {
            db.lovedDao.deleteWord(word)
        }
    }

    suspend fun isLoved(word: String): Boolean {
        return withContext(Dispatchers.IO) {
            db.lovedDao.isWordExists(word)
        }
    }

    fun toModel(entity: LovedWordEntity) = LovedWordModel(
        word = entity.word,
    )
}
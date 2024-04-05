package ru.kpfu.itis.ponomarev.lexify.domain.repository

import ru.kpfu.itis.ponomarev.lexify.domain.model.LovedWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.sorting.LovedWordsSorting

interface LovedRepository {

    suspend fun getAll(sorting: LovedWordsSorting): List<LovedWordModel>
    suspend fun getRecent(limit: Int): List<LovedWordModel>
    suspend fun addWord(word: String)
    suspend fun deleteWord(word: String)
    suspend fun isLoved(word: String): Boolean
}
package ru.kpfu.itis.ponomarev.lexify.domain.repository

import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel

interface WordRepository {

    suspend fun getDefinitions(word: String): List<WordDefinitionModel>
}
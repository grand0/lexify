package ru.kpfu.itis.ponomarev.lexify.data.remote.api

import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordAudioDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordDefinitionDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordExampleDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordExamplesDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordRelatedWordsDataModel

interface WordApi {
    suspend fun getDefinitions(word: String): List<WordDefinitionDataModel>
    suspend fun getEtymologies(word: String): List<String>
    suspend fun getExamples(word: String): WordExamplesDataModel
    suspend fun getRelatedWords(word: String): List<WordRelatedWordsDataModel>
    suspend fun getAudio(word: String): List<WordAudioDataModel>
}
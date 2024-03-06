package ru.kpfu.itis.ponomarev.lexify.domain.repository

import ru.kpfu.itis.ponomarev.lexify.domain.model.RelatedWordsModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordAudioModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel

interface WordRepository {

    suspend fun getDefinitions(word: String): List<WordDefinitionModel>
    suspend fun getEtymologies(word: String): WordEtymologiesModel
    suspend fun getExamples(word: String): List<WordExampleModel>
    suspend fun getRelatedWords(word: String): List<RelatedWordsModel>
    suspend fun getAudio(word: String): List<WordAudioModel>
}
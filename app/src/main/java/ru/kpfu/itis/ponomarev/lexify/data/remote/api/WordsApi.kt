package ru.kpfu.itis.ponomarev.lexify.data.remote.api

import ru.kpfu.itis.ponomarev.lexify.data.remote.model.RandomWordDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordOfTheDayDataModel

interface WordsApi {
    suspend fun getRandomWord(): RandomWordDataModel
    suspend fun getRandomWords(limit: Int): List<RandomWordDataModel>
    suspend fun getWordOfTheDay(): WordOfTheDayDataModel
}
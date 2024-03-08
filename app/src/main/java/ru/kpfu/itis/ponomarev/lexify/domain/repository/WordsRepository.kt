package ru.kpfu.itis.ponomarev.lexify.domain.repository

import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel

interface WordsRepository {

    suspend fun getRandomWord(): RandomWordModel
    suspend fun getRandomWords(limit: Int = 10): List<RandomWordModel>
    suspend fun getWordOfTheDay(): WordOfTheDayModel
}
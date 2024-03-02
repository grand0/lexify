package ru.kpfu.itis.ponomarev.lexify.domain.repository

import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel

interface WordsRepository {

    suspend fun getRandomWord(): RandomWordModel
    suspend fun getRandomWords(): List<RandomWordModel>
    suspend fun getWordOfTheDay(): WordOfTheDayModel
}
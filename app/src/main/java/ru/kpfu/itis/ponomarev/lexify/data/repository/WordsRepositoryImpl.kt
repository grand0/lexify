package ru.kpfu.itis.ponomarev.lexify.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import ru.kpfu.itis.ponomarev.lexify.data.remote.WordsResources
import ru.kpfu.itis.ponomarev.lexify.domain.model.RandomWordModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordOfTheDayModel
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordsRepository
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val http: HttpClient,
) : WordsRepository {

    override suspend fun getRandomWord(): RandomWordModel =
        http.get(WordsResources.RandomWord()).body()

    override suspend fun getRandomWords(limit: Int): List<RandomWordModel> =
        http.get(WordsResources.RandomWords(limit = limit)).body()

    override suspend fun getWordOfTheDay(): WordOfTheDayModel {
        return http.get(WordsResources.WordOfTheDay()).body()
    }
}
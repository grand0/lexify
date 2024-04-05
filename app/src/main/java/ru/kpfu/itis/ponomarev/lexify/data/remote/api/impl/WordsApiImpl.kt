package ru.kpfu.itis.ponomarev.lexify.data.remote.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.WordsApi
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.RandomWordDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordOfTheDayDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.resources.WordsResources
import javax.inject.Inject

class WordsApiImpl @Inject constructor(
    private val http: HttpClient,
) : WordsApi {
    override suspend fun getRandomWord(): RandomWordDataModel =
        http.get(WordsResources.RandomWord()).body()

    override suspend fun getRandomWords(limit: Int): List<RandomWordDataModel> =
        http.get(WordsResources.RandomWords(limit = limit)).body()

    override suspend fun getWordOfTheDay(): WordOfTheDayDataModel =
        http.get(WordsResources.WordOfTheDay()).body()
}
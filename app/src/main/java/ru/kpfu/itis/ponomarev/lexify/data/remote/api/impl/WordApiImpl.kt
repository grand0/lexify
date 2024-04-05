package ru.kpfu.itis.ponomarev.lexify.data.remote.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import ru.kpfu.itis.ponomarev.lexify.data.remote.api.WordApi
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordAudioDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordDefinitionDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordExampleDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordExamplesDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordRelatedWordsDataModel
import ru.kpfu.itis.ponomarev.lexify.data.remote.resources.WordResources
import javax.inject.Inject

class WordApiImpl @Inject constructor(
    private val http: HttpClient,
) : WordApi {
    override suspend fun getDefinitions(word: String): List<WordDefinitionDataModel> =
        http.get(WordResources.Word.Definitions(WordResources.Word(word = word))).body()

    override suspend fun getEtymologies(word: String): List<String> =
        http.get(WordResources.Word.Etymologies(WordResources.Word(word = word))).body()

    override suspend fun getExamples(word: String): WordExamplesDataModel =
        http.get(WordResources.Word.Examples(WordResources.Word(word = word))).body()

    override suspend fun getRelatedWords(word: String): List<WordRelatedWordsDataModel> =
        http.get(WordResources.Word.RelatedWords(WordResources.Word(word = word))).body()

    override suspend fun getAudio(word: String): List<WordAudioDataModel> =
        http.get(WordResources.Word.Audio(WordResources.Word(word = word))).body()
}
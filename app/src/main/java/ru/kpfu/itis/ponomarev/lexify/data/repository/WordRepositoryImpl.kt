package ru.kpfu.itis.ponomarev.lexify.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import ru.kpfu.itis.ponomarev.lexify.data.remote.WordResources
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.response.WordDefinitionResponse
import ru.kpfu.itis.ponomarev.lexify.domain.repository.WordRepository
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val http: HttpClient,
) : WordRepository{

    override suspend fun getDefinitions(word: String): List<WordDefinitionModel> {
        val resp: List<WordDefinitionResponse> =
            http.get(WordResources.Word.Definitions(WordResources.Word(word = word))).body()
        return resp
            .filter { it.text != null }
            .map {
                WordDefinitionModel(
                    partOfSpeech = it.partOfSpeech,
                    attributionText = it.attributionText,
                    attributionUrl = it.attributionUrl,
                    text = it.text!!,
                    labels = it.labels,
                )
            }
    }
}
package ru.kpfu.itis.ponomarev.lexify.data.repository

import androidx.core.text.HtmlCompat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import ru.kpfu.itis.ponomarev.lexify.data.remote.WordResources
import ru.kpfu.itis.ponomarev.lexify.domain.model.RelatedWordsModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordAudioModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordEtymologiesModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordExampleModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.response.WordDefinitionResponse
import ru.kpfu.itis.ponomarev.lexify.domain.model.response.WordExamplesResponse
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
                val id = it.id ?: it.hashCode().toString()
                WordDefinitionModel(
                    id = id,
                    partOfSpeech = it.partOfSpeech,
                    attributionText = it.attributionText,
                    attributionUrl = it.attributionUrl,
                    text = HtmlCompat.fromHtml(it.text!!, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
                    labels = it.labels,
                )
            }
    }

    override suspend fun getEtymologies(word: String): WordEtymologiesModel {
        val resp: List<String> =
            http.get(WordResources.Word.Etymologies(WordResources.Word(word = word))).body()
        return WordEtymologiesModel(HtmlCompat.fromHtml(resp.first(), HtmlCompat.FROM_HTML_MODE_COMPACT).toString())
    }

    override suspend fun getExamples(word: String): List<WordExampleModel> {
        val resp: WordExamplesResponse =
            http.get(WordResources.Word.Examples(WordResources.Word(word = word))).body()
        return resp.examples
            .distinctBy(WordExampleModel::text)
    }

    override suspend fun getRelatedWords(word: String): List<RelatedWordsModel> {
        return http.get(WordResources.Word.RelatedWords(WordResources.Word(word = word))).body()
    }

    override suspend fun getAudio(word: String): List<WordAudioModel> {
        return http.get(WordResources.Word.Audio(WordResources.Word(word = word))).body()
    }
}
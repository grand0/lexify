package ru.kpfu.itis.ponomarev.lexify.data.remote.model.mapper

import androidx.core.text.HtmlCompat
import ru.kpfu.itis.ponomarev.lexify.data.remote.model.WordDefinitionDataModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionLabelModel
import ru.kpfu.itis.ponomarev.lexify.domain.model.WordDefinitionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordDefinitionModelMapper @Inject constructor() {

    fun mapDataModelToModel(data: WordDefinitionDataModel): WordDefinitionModel? {
        if (data.text == null) {
            return null
        }
        val cleanText = HtmlCompat.fromHtml(data.text, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
        return WordDefinitionModel(
            id = data.id ?: defaultId(data),
            partOfSpeech = data.partOfSpeech,
            attributionText = data.attributionText,
            attributionUrl = data.attributionUrl,
            text = cleanText,
            labels = data.labels.map { WordDefinitionLabelModel(type = it.type, text = it.text) },
            word = data.word,
        )
    }

    private fun defaultId(data: WordDefinitionDataModel) = data.word + data.hashCode()
}